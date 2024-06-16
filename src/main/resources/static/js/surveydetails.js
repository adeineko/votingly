import {token, header} from "./util/csrf.js";

const handleUpdateButton = document.getElementById("saveChangesBtn");

let surveyIdInput = document.getElementById("surveyId");

let questionCount = 0;
let questions = [];

const surveyNameInput = document.getElementById("surveyName");
const surveyTypeInput = document.getElementById("surveyType");

function populateSurveyDetails(surveyName, surveyType, questionsData) {
    surveyNameInput.value = surveyName;
    surveyTypeInput.value = surveyType;

    questionsData.forEach((question, index) => {
        createQuestion(question, index + 1);
    });
}

async function getSurveyDetails() {
    try {
        const response = await fetch(`/api/surveys/${surveyIdInput.value}/details`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                [header]: token
            }
        });

        if (!response.ok) {
            throw new Error('Network response was not ok.');
        }

        const data = await response.json();
        questions = data.questions;
        populateSurveyDetails(data.surveyName, data.surveyType, questions);
    } catch (error) {
        console.error("Failed to fetch survey details:", error);
        alert("Failed to fetch survey details. Please check the console for more details.");
    }
}

function createQuestion(question = {questionName: "", questionType: "OPEN", options: []}, questionNumber) {
    questionCount++;
    const questionDiv = document.createElement("div");
    questionDiv.setAttribute('id', `questionBlock${questionNumber}`);
    questionDiv.classList.add("mb-3", "question-block");

    questionDiv.innerHTML = `
        <div class="d-flex justify-content-between align-items-center">
            <label for="question${questionNumber}" class="form-label">Question ${questionNumber}</label>
        </div>
        <input type="text" class="form-control" id="question${questionNumber}" name="questions[${questionNumber}][questionName]" value="${question.questionName}" required>
        <select class="form-select mt-2" id="questionType${questionNumber}" name="questions[${questionNumber}][questionType]">
            <option value="OPEN" ${question.questionType === 'OPEN' ? 'selected' : ''}>Open</option>
            <option value="CHOICE" ${question.questionType === 'CHOICE' ? 'selected' : ''}>Choice</option>
            <option value="RANGE" ${question.questionType === 'RANGE' ? 'selected' : ''}>Range</option>
        </select>
        <div id="choicesContainer${questionNumber}" class="choices-container mt-2"></div>
    `;

    document.getElementById("questionContainer").appendChild(questionDiv);
    addTypeChangeListener(questionNumber, question.questionType);

    if (question.questionType === 'CHOICE') {
        question.options.forEach(choice => {
            addChoiceInput(questionNumber, choice.optionText);
        });
    } else if (question.questionType === 'RANGE') {
        addRangeInputs(questionNumber, question);
    }

    if (questionCount > 1) {
        addRemoveButton(questionDiv, questionNumber);
    }
}

function addTypeChangeListener(count, type) {
    const typeSelect = document.getElementById(`questionType${count}`);
    typeSelect.addEventListener('change', () => handleTypeChange(count));
    handleTypeChange(count, type);
}

function handleTypeChange(questionNumber, type) {
    const typeValue = type || document.getElementById(`questionType${questionNumber}`).value;
    const choicesContainer = document.getElementById(`choicesContainer${questionNumber}`);
    choicesContainer.innerHTML = '';

    if (typeValue === 'CHOICE') {
        const addChoiceBtn = document.createElement("button");
        addChoiceBtn.textContent = "Add Choice";
        addChoiceBtn.type = "button";
        addChoiceBtn.className = "btn btn-info btn-sm mt-2";
        addChoiceBtn.onclick = () => addChoiceInput(questionNumber);
        choicesContainer.appendChild(addChoiceBtn);
        if (!type) {
            addChoiceInput(questionNumber);
        }
    }
}

function addChoiceInput(questionNumber, value = "") {
    const choiceContainer = document.createElement("div");
    choiceContainer.className = "input-group mb-2";

    const choiceInput = document.createElement("input");
    choiceInput.type = "text";
    choiceInput.className = "form-control";
    choiceInput.placeholder = "Enter choice";
    choiceInput.value = value;
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

function addRangeInputs(questionNumber, question = {}) {
    const choicesContainer = document.getElementById(`choicesContainer${questionNumber}`);

    const minInput = document.createElement("input");
    minInput.type = "number";
    minInput.className = "form-control mt-2";
    minInput.placeholder = "Minimum value";
    minInput.id = `minInput${questionNumber}`;
    minInput.value = question.min;
    minInput.name = `questions[${questionNumber}][min]`;

    const maxInput = document.createElement("input");
    maxInput.type = "number";
    maxInput.className = "form-control mt-2";
    maxInput.placeholder = "Maximum value";
    maxInput.id = `maxInput${questionNumber}`;
    maxInput.value = question.max;
    maxInput.name = `questions[${questionNumber}][max]`;

    const stepInput = document.createElement("input");
    stepInput.type = "number";
    stepInput.className = "form-control mt-2";
    stepInput.placeholder = "Step value";
    stepInput.id = `stepInput${questionNumber}`;
    stepInput.value = question.step;
    stepInput.name = `questions[${questionNumber}][step]`;

    choicesContainer.appendChild(minInput);
    choicesContainer.appendChild(maxInput);
    choicesContainer.appendChild(stepInput);
}

function removeQuestion(questionNumber) {
    const questionDiv = document.getElementById(`questionBlock${questionNumber}`);
    if (questionDiv) {
        questionDiv.parentNode.removeChild(questionDiv);

        const remainingQuestions = document.querySelectorAll('.question-block');
        remainingQuestions.forEach((question, index) => {
            question.id = `questionBlock${index + 1}`;
            question.querySelector('.remove-question-btn').dataset.questionId = index + 1;
            question.querySelector('label').textContent = `Question ${index + 1}`;
        });

        const lastQuestionDiv = document.querySelector('.question-block:last-child');
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

async function removeSurvey() {
    const surveyId = surveyIdInput.value;
    try {
        const response = await fetch(`/api/surveys/${surveyId}`, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json',
                [header]: token
            }
        });

        if (!response.ok) {
            throw new Error('Network response was not ok.');
        }

        const successContainer = document.getElementById('successContainer')
        successContainer.innerHTML = 'Deleted successfully!'
        successContainer.style.display = 'block'
        setTimeout(() => {
            window.location.href = '/surveys';
        }, 1000);
    } catch (error) {
        console.error("Failed to delete survey:", error);
        alert("Failed to delete survey. Please check the console for more details.");
    }
}

async function handleUpdate() {
    const updatedQuestions = [];

    document.querySelectorAll('.question-block').forEach((questionBlock, index) => {
        const questionNumber = index + 1;
        const questionNameTextArea = questionBlock.querySelector(`#question${questionNumber}`);
        const questionType = questionBlock.querySelector(`#questionType${questionNumber}`);

        updatedQuestions.push({
            id: questionNumber,
            questionName: questionNameTextArea.value,
            questionType: questionType.value
        });
    });

    const response = await fetch(`/api/surveys/${surveyIdInput.value}`, {
        method: 'PATCH',
        headers: {
            'Content-type': 'application/json',
            [header]: token
        },
        body: JSON.stringify({
            surveyName: surveyNameInput.value,
            surveyType: surveyTypeInput.value,
            questions: updatedQuestions
        })
    })
    if (response.status === 204) {
        handleUpdateButton.disabled = true
    } else {
        alert('Something went wrong!' + response.status)
    }
}

document.addEventListener('DOMContentLoaded', () => {
    document.getElementById("addQuestionBtn").addEventListener("click", createQuestion);

    document.addEventListener('input', (event) => {
        const target = event.target;
        if (target.matches('input, select')) {
            handleUpdateButton.disabled = false;
        }
    });

    document.getElementById("removeSurveyBtn").addEventListener("click", removeSurvey);

    getSurveyDetails();
});

handleUpdateButton?.addEventListener('click', handleUpdate)
