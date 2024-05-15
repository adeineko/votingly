import { token, header } from "./util/csrf.js";

let surveyIdInput = document.getElementById("surveyId");

console.log("surveyIdInput", surveyIdInput);

let questionCount = 0;
let questions = []; // Variable to store all questions

// Sample survey data
const sampleSurvey = {
    surveyName: "Sample Survey",
    surveyType: "CIRCULAR",
    questions: [
        { text: "Sample question 1", type: "open" },
        { text: "Sample question 2", type: "multipleChoice", choices: ["Option 1", "Option 2"] },
        { text: "Sample question 3", type: "open" }
    ]
};

const surveyNameInput = document.getElementById("surveyName");
const surveyTypeInput = document.getElementById("surveyType");

// Function to populate survey details
function populateSurveyDetails(surveyName, surveyType) {
    surveyNameInput.value = surveyName;
    surveyTypeInput.value = surveyType;

    questions.forEach(question => {
        createQuestion(question);
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
        console.log(data); // Log data to see what is actually being returned.
        questions = data.questions
        // Populate survey details on page load
        populateSurveyDetails(data.surveyName, data.surveyType);
        return data;
    } catch (error) {
        console.error("Failed to fetch survey details:", error);
        alert("Failed to fetch survey details. Please check the console for more details.");
    }

}

// Function to create a new question block
function createQuestion(question = { text: "", type: "open", choices: [] }) {
    questionCount++;
    const questionDiv = document.createElement("div");
    questionDiv.setAttribute('id', `questionBlock${questionCount}`);
    questionDiv.classList.add("mb-3", "question-block");

    questionDiv.innerHTML = `
        <div class="d-flex justify-content-between align-items-center">
            <label for="question${questionCount}" class="form-label">Question ${questionCount}</label>
        </div>
        <input type="text" class="form-control" id="question${questionCount}" name="questions[${questionCount}][text]" value="${question.questionName}" required>
        <select class="form-select mt-2" id="questionType${questionCount}" name="questions[${questionCount}][type]">
            <option value="open" ${question.questionType === 'open' ? 'selected' : ''}>Open</option>
            <option value="multipleChoice" ${question.questionType === 'multipleChoice' ? 'selected' : ''}>Multiple Choice</option>
        </select>
        <div id="choicesContainer${questionCount}" class="choices-container mt-2"></div>
    `;

    document.getElementById("questionContainer").appendChild(questionDiv);
    addTypeChangeListener(questionCount, question.questionType);

    // Add "Remove" button to the new last question
    if (questionCount > 1) {
        addRemoveButton(questionDiv, questionCount);
    }

    // Populate choices if question type is multipleChoice
    if (question.questionType === 'multipleChoice') {
        question.options.forEach(choice => {
            addChoiceInput(questionCount, choice);
        });
    }
}

// Adds change listener to question type selector
function addTypeChangeListener(count, type) {
    const typeSelect = document.getElementById(`questionType${count}`);
    typeSelect.addEventListener('change', () => handleTypeChange(count));
    handleTypeChange(count, type); // Initialize choices container visibility
}

// Handles changes to question type
function handleTypeChange(questionNumber, type) {
    const typeValue = type || document.getElementById(`questionType${questionNumber}`).value;
    const choicesContainer = document.getElementById(`choicesContainer${questionNumber}`);

    if (typeValue === 'multipleChoice') {
        choicesContainer.innerHTML = ''; // Clear previous choices
        const addChoiceBtn = document.createElement("button");
        addChoiceBtn.textContent = "Add Choice";
        addChoiceBtn.type = "button";
        addChoiceBtn.className = "btn btn-info btn-sm mt-2";
        addChoiceBtn.onclick = () => addChoiceInput(questionNumber);
        choicesContainer.appendChild(addChoiceBtn);
        if (type !== 'multipleChoice') {
            addChoiceInput(questionNumber); // Add first choice input immediately
        }
        choicesContainer.style.display = 'block';
    } else {
        choicesContainer.innerHTML = ''; // Clear choices
        choicesContainer.style.display = 'none';
    }
}

// Adds a new choice input for a multiple choice question
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

// Function to remove a question block
function removeQuestion(questionNumber) {
    const questionDiv = document.getElementById(`questionBlock${questionNumber}`);
    if (questionDiv) {
        questionDiv.parentNode.removeChild(questionDiv);

        // Update IDs of remaining questions
        const remainingQuestions = document.querySelectorAll('.question-block');
        remainingQuestions.forEach((question, index) => {
            const oldQuestionNumber = parseInt(question.id.replace('questionBlock', ''));
            question.id = `questionBlock${index + 1}`;
            question.querySelector('.remove-question-btn').dataset.questionId = index + 1;
            question.querySelector('label').textContent = `Question ${index + 1}`;
        });

        // Add "Remove" button to new last question, if it exists
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

// Function to add "Remove" button to a question
function addRemoveButton(questionDiv, questionNumber) {
    const removeButton = document.createElement("button");
    removeButton.type = "button";
    removeButton.className = "btn btn-danger btn-sm remove-question-btn";
    removeButton.textContent = "Remove Question";
    removeButton.dataset.questionId = questionNumber;
    removeButton.addEventListener('click', () => removeQuestion(questionNumber));
    questionDiv.querySelector('.d-flex').appendChild(removeButton);
}

// Function to remove the survey
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

        console.log("Survey deleted successfully!");
        alert("Survey deleted successfully!")
        window.location.href = '/surveys';
    } catch (error) {
        console.error("Failed to delete survey:", error);
        alert("Failed to delete survey. Please check the console for more details.");
    }
}

// Adds global event listeners on document ready
document.addEventListener('DOMContentLoaded', () => {
    document.getElementById("addQuestionBtn").addEventListener("click", createQuestion);

    // Edit survey name
    document.getElementById("surveyName").addEventListener("click", enableEdit("surveyName"));
    // Edit survey type
    document.getElementById("surveyType").addEventListener("click", enableEdit("surveyType"));

    // Enable save changes button when any input changes
    document.querySelectorAll('input, select').forEach(input => {
        input.addEventListener('input', () => {
            document.getElementById("saveChangesBtn").disabled = false;
        });
    });

    document.getElementById("removeSurveyBtn").addEventListener("click", removeSurvey);

    // Save changes
    document.getElementById("saveChangesBtn").addEventListener("click", saveChanges);
});

// Function to enable editing of survey details
function enableEdit(elementId) {
    const element = document.getElementById(elementId);
    element.disabled = !element.disabled;
}

// Function to save changes
function saveChanges() {
    // You can implement the logic to save changes to the server here
    console.log("Changes saved!");
    // After saving changes, you may want to disable the save button again
    document.getElementById("saveChangesBtn").disabled = true;
}

window.addEventListener('load', getSurveyDetails);
