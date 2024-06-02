import {token, header} from "./util/csrf.js";

let questionCount = 0;

// Create initial questions
for (let i = 0; i < 3; i++) {
    createQuestion();
}

/**
 * Removes a question block from the DOM.
 * @param {number} questionNumber - The number of the question to be removed.
 */
function removeQuestion(questionNumber) {
    const questionDiv = document.getElementById(`questionBlock${questionNumber}`);
    if (questionDiv) {
        const container = questionDiv.parentNode;
        container.removeChild(questionDiv);

        // Decrement the question count
        questionCount--;

        // Update IDs and labels of remaining questions
        const remainingQuestions = container.querySelectorAll('.question-block');
        remainingQuestions.forEach((question, index) => {
            const newQuestionNumber = index + 1;
            question.id = `questionBlock${newQuestionNumber}`;
            question.querySelector('.remove-question-btn').dataset.questionId = newQuestionNumber;
            question.querySelector('label').textContent = `Question ${newQuestionNumber}`;
        });

        // Add "Remove" button to new last question, if it exists
        const lastQuestionDiv = container.querySelector('.question-block:last-child');
        if (lastQuestionDiv && questionCount > 1) {
            const removeButton = lastQuestionDiv.querySelector('.remove-question-btn');
            if (!removeButton) {
                addRemoveButton(lastQuestionDiv, questionCount);
            }
        }
    }
}

/**
 * Adds a "Remove Question" button to a question block.
 * @param {HTMLElement} questionDiv - The question block element.
 * @param {number} questionNumber - The number of the question.
 */
function addRemoveButton(questionDiv, questionNumber) {
    const removeButton = document.createElement("button");
    removeButton.type = "button";
    removeButton.className = "btn btn-danger btn-sm remove-question-btn";
    removeButton.textContent = "Remove Question";
    removeButton.dataset.questionId = questionNumber;
    removeButton.addEventListener('click', () => removeQuestion(questionNumber));
    questionDiv.querySelector('.d-flex').appendChild(removeButton);
}

/**
 * Creates a new question block and appends it to the DOM.
 */
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
            <option value="choice">Choice question</option>
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

/**
 * Adds a change listener to the question type selector.
 * @param {number} count - The current question count.
 */
function addTypeChangeListener(count) {
    const typeSelect = document.getElementById(`questionType${count}`);
    typeSelect.addEventListener('change', () => handleTypeChange(count));
}

/**
 * Handles changes to the question type selector.
 * @param {number} questionNumber - The number of the question.
 */
function handleTypeChange(questionNumber) {
    const typeValue = document.getElementById(`questionType${questionNumber}`).value;
    const choicesContainer = document.getElementById(`choicesContainer${questionNumber}`);
    choicesContainer.innerHTML = '';

    if (typeValue === 'choice') {
        const addChoiceBtn = document.createElement("button");
        addChoiceBtn.textContent = "Add Choice";
        addChoiceBtn.type = "button";
        addChoiceBtn.className = "btn btn-info btn-sm mt-2";
        addChoiceBtn.onclick = () => addChoiceInput(questionNumber);
        choicesContainer.appendChild(addChoiceBtn);

        const checkIsMultiLabel = document.createElement("label");
        checkIsMultiLabel.textContent = "Multiple choice";
        checkIsMultiLabel.className = "mt-2 d-column";

        const checkIsMulti = document.createElement("input");
        checkIsMulti.type = "checkbox";
        checkIsMulti.className = "form-check-input";
        checkIsMulti.id = `checkBoxInput${questionNumber}`;
        checkIsMulti.name = `questions[${questionNumber}][isMultiple]`;

        choicesContainer.appendChild(checkIsMulti);
        choicesContainer.appendChild(checkIsMultiLabel);

        addChoiceInput(questionNumber); // Add first choice input immediately
    }
}

/**
 * Adds a new choice input for a multiple choice or single choice question.
 * @param {number} questionNumber - The number of the question.
 */
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
 * Adds global event listeners on document ready.
 */
document.addEventListener('DOMContentLoaded', () => {
    document.getElementById("addQuestionBtn").addEventListener("click", createQuestion);
    document.getElementById("surveyForm").addEventListener("submit", submitSurvey);
});

const surveyNameInput = document.getElementById("surveyName");
const surveyTypeInput = document.getElementById("surveyType");

/**
 * Submits the survey data to the server.
 * @param {Event} event - The form submission event.
 */
async function submitSurvey(event) {
    event.preventDefault();

    const survey = {
        surveyName: surveyNameInput.value,
        surveyType: surveyTypeInput.value,
        questions: []
    };

    let count = 1;

    // Iterate over each question block
    document.querySelectorAll('.question-block').forEach(questionBlock => {
        const questionName = questionBlock.querySelector('input[type="text"]').value;
        const questionType = document.getElementById(`questionType${count}`).value.toUpperCase();
        const choices = [];

        let question = {
            questionName: questionName,
            questionType: questionType,
        };

        // If question type is "CHOICE", gather choices and define isMultiple
        if (questionType === 'CHOICE') {
            const checkBoxInput = document.getElementById(`checkBoxInput${count}`);
            question.isMultipleChoice = checkBoxInput.checked;
            questionBlock.querySelectorAll(`input[name="questions[${count}][choices][]"]`).forEach(choiceInput => {
                choices.push(choiceInput.value);
            });

            question.options = choices.map(choice => ({optionText: choice}));
        } else if (questionType === 'RANGE') {
        }

        survey.questions.push(question);
        count++;
    });

    console.log(survey);

    const response = await fetch('/api/surveys', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json',
            [header]: token
        },
        body: JSON.stringify(survey)
    });

    if (response.status === 201) {
        alert('Survey created successfully!');
        window.location.href = '/surveys';
    } else {
        alert('Something went wrong. Please try again.' + ' ' + response.status);
    }
}
