import {token, header} from "./util/csrf.js";

let questionCount = 0;

// Create initial questions
for (let i = 0; i < 3; i++) {
    createQuestion();
}

function removeQuestion(questionNumber) {
    const questionDiv = document.getElementById(`questionBlock${questionNumber}`);
    if (questionDiv) {
        const container = questionDiv.parentNode;
        container.removeChild(questionDiv);

        // Update IDs of remaining questions
        const remainingQuestions = container.querySelectorAll('.question-block');
        remainingQuestions.forEach((question, index) => {
            const oldQuestionNumber = parseInt(question.id.replace('questionBlock', ''));
            question.id = `questionBlock${index + 1}`;
            question.querySelector('.remove-question-btn').dataset.questionId = index + 1;
            question.querySelector('label').textContent = `Question ${index + 1}`;
        });

        // Add "Remove" button to new last question, if it exists
        const lastQuestionDiv = container.querySelector('.question-block:last-child');
        if (lastQuestionDiv && questionCount > 1) {
            const removeButton = lastQuestionDiv.querySelector('.remove-question-btn');
            if (!removeButton) {
                addRemoveButton(lastQuestionDiv, questionCount - 1);
            }
        }
    }
    questionCount--;
}

function addRemoveButton(questionDiv, questionNumber) {
    const removeButton = document.createElement("button");
    removeButton.type = "button";
    removeButton.className = "btn btn-danger btn-sm remove-question-btn";
    removeButton.textContent = "Remove Question";
    removeButton.dataset.questionId = questionNumber;
    removeButton.addEventListener('click', () => removeQuestion(questionNumber));
    questionDiv.querySelector('.d-flex').appendChild(removeButton);
}

function createQuestion() {
    questionCount++;
    const questionDiv = document.createElement("div");
    questionDiv.setAttribute('id', `questionBlock${questionCount}`);
    questionDiv.classList.add("mb-3", "question-block");

    questionDiv.innerHTML = `
        <div class="d-flex justify-content-between align-items-center">
            <label for="question${questionCount}" class="form-label">Question ${questionCount}</label>
        </div>
        <input type="text" class="form-control" id="question${questionCount}" name="questions[${questionCount}][name]" required>
        <select class="form-select mt-2" id="questionType${questionCount}" name="questions[${questionCount}][type]">
            <option value="open">Open question</option>
            <option value="multipleChoice">Multiple Choice</option>
            <option value="singleChoice">Single Choice</option>
            <option value="range">Range question</option>
        </select>
        <div id="choicesContainer${questionCount}" class="choices-container mt-2"></div>
    `;

    document.getElementById("questionContainer").appendChild(questionDiv);
    addTypeChangeListener(questionCount);

    // Remove "Remove" button from previous last question, if exists
    const previousLastQuestionDiv = document.getElementById(`questionBlock${questionCount - 1}`);
    if (previousLastQuestionDiv) {
        const removeButton = previousLastQuestionDiv.querySelector('.remove-question-btn');
        if (removeButton) {
            removeButton.remove();
        }
    }

    // Add "Remove" button to the new last question
    if (questionCount > 1) {
        addRemoveButton(questionDiv, questionCount);
    }
}

// Adds change listener to question type selector
function addTypeChangeListener(count) {
    const typeSelect = document.getElementById(`questionType${count}`);
    typeSelect.addEventListener('change', () => handleTypeChange(count));
}

// Handles changes to question type
function handleTypeChange(questionNumber) {
    const typeValue = document.getElementById(`questionType${questionNumber}`).value;
    const choicesContainer = document.getElementById(`choicesContainer${questionNumber}`);
    choicesContainer.innerHTML = '';

    if (typeValue === 'multipleChoice') {
        const addChoiceBtn = document.createElement("button");
        addChoiceBtn.textContent = "Add Choice";
        addChoiceBtn.type = "button";
        addChoiceBtn.className = "btn btn-info btn-sm mt-2";
        addChoiceBtn.onclick = () => addChoiceInput(questionNumber);
        choicesContainer.appendChild(addChoiceBtn);
        addChoiceInput(questionNumber); // Add first choice input immediately
    }
}

// Adds a new choice input for a multiple choice question
function addChoiceInput(questionNumber) {
    const choiceContainer = document.createElement("div");
    choiceContainer.className = "input-group mb-2";

    const choiceInput = document.createElement("input");
    choiceInput.type = "text";
    choiceInput.className = "form-control";
    choiceInput.placeholder = "Enter choice";
    choiceInput.name = `questions[${questionNumber}][choices][]`;

    const removeChoiceBtn = document.createElement("button");
    removeChoiceBtn.type = "button";
    removeChoiceBtn.className = "btn btn-danger";
    removeChoiceBtn.textContent = "Remove";
    removeChoiceBtn.onclick = () => choiceContainer.remove();

    choiceContainer.appendChild(choiceInput);
    choiceContainer.appendChild(removeChoiceBtn);

    document.getElementById(`choicesContainer${questionNumber}`).appendChild(choiceContainer);
}

/**
 *  Adds global event listeners on document
 **/
document.addEventListener('DOMContentLoaded', () => {
    document.getElementById("addQuestionBtn").addEventListener("click", createQuestion);
    document.getElementById("surveyForm").addEventListener("submit", submitSurvey);
    document.addEventListener('click', event => {
        if (event.target.classList.contains('remove-question-btn')) {
            removeQuestion(event.target.dataset.questionId);
        }
    });
});

/**
 * Submits the survey data to the server
 **/
async function submitSurvey(event) {
    event.preventDefault();

    const survey = {
        surveyName: document.getElementById("surveyName").value,
        surveyType: document.getElementById("surveyType").value,
    };

    // let count = 1;

    // Iterate over each question block
    document.querySelectorAll('.question-block').forEach(questionBlock => {
        // const questionName = questionBlock.querySelector('input[type="text"]').value;
        // const questionType = document.getElementById("questionType" + count).value.toUpperCase();
        // const choices = [];
        // count++;
        const questionName = questionBlock.querySelector('input[type="text"]').value;
        const questionType = questionBlock.querySelector('select').value.toUpperCase();
        const choices = [];

        let question = {
            questionName: questionName,
            questionType: questionType,
        };
        // If question type is "multipleChoice", gather choices
        if (questionType === 'MULTIPLECHOICE') {
            questionBlock.querySelectorAll('input[name^="questions"]').forEach(choiceInput => {
                choices.push(choiceInput.value);
            });
            question = {
                ...question,
                isMultiChoice: true,
                options: choices
            };
        } else if (questionType === 'SINGLECHOICE') {
            questionBlock.querySelectorAll('input[name^="questions"]').forEach(choiceInput => {
                choices.push(choiceInput.value);
            });
            question = {
                ...question,
                isMultiChoice: false,
                options: choices
            };
        } else if (questionType === 'RANGE') {

        }

        survey.questions.push(question);
    });

    console.log(survey);
    console.log(survey.questions);

    const response = await fetch('/api/surveys', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json',
            [header]: token
        },
        body: JSON.stringify(survey)
    });
//TODO: add validation
    if (response.status === 201) {
        alert('Survey created successfully!');
        window.location.href = '/surveys';
    } else {
        alert('Something went wrong. Please try again.');
    }
}
