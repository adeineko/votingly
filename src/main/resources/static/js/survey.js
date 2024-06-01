import {header, token} from "./util/csrf.js";

const questionsContainer = document.getElementById("questionsContainer");
const nameContainer = document.getElementById("nameContainer");
const surveyIdInput = document.getElementById("surveyId");
const surveyNameInput = document.getElementById("surveyName");

let currentQuestionIndex = 0;
let questions = []; // Variable to store all questions


nameContainer.innerHTML = surveyNameInput.value;

async function fetchFirstQuestion() {
    const response = await fetch(`/api/surveys/${surveyIdInput.value}/questions`,
        {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                [header]: token

            }
        });
    if (response.status === 200) {
        questions = await response.json();
        // Display the first question
        displayQuestion(questions[currentQuestionIndex]);
    }
}

async function fetchNextQuestion() {
    currentQuestionIndex++;
    // Fetch the next question
    if (currentQuestionIndex < questions.length) {
        // If there are more questions, fetch the next one
        displayQuestion(questions[currentQuestionIndex]);
    } else {
        // If there are no more questions, redirect to thank-you page
        window.location.href = `/thank-you-page`;
    }
}

function createOpenQuestion(question) {
    const questionDiv = document.createElement('div');
    questionDiv.classList.add('col-lg-10');

    const cardBody = document.createElement('div');
    cardBody.classList.add('card-body');
    questionDiv.appendChild(cardBody);

    const cardTitle = document.createElement('h5');
    cardTitle.classList.add('card-title');
    cardTitle.textContent = question.questionName;
    cardBody.appendChild(cardTitle);

    const input = document.createElement('input');
    input.setAttribute('type', 'text');
    input.classList.add('form-control');
    input.setAttribute('id', 'openQuestionInput');
    cardBody.appendChild(input);

    return questionDiv;
}

function createChoiceQuestion(question, isMultiChoice) {
    const questionDiv = document.createElement('div');
    questionDiv.classList.add('col-lg-10');

    const cardBody = document.createElement('div');
    cardBody.classList.add('card-body');
    questionDiv.appendChild(cardBody);

    const cardTitle = document.createElement('h5');
    cardTitle.classList.add('card-title');
    cardTitle.textContent = question.questionName;
    cardBody.appendChild(cardTitle);

    question.options.forEach(option => {
        const optionDiv = document.createElement('div');

        const input = document.createElement('input');

        input.setAttribute('type', isMultiChoice ? 'checkbox' : 'radio');
        input.setAttribute('name', isMultiChoice ? `options_${question.id}[]` : `options_${question.id}`);
        input.setAttribute('value', option.optionText);
        input.setAttribute('data-option-id', option.optionId);

        const optionLabel = document.createElement('label');
        optionLabel.textContent = option.optionText;

        optionDiv.appendChild(input);
        optionDiv.appendChild(optionLabel);

        cardBody.appendChild(optionDiv);
    });

    return questionDiv;
}

//TODO: change innerHTML to createElement()
function createRangeQuestion(question) {
    const questionDiv = document.createElement('div');
    questionDiv.classList.add('col-lg-10');
    let optionsHTML = '';
    for (let i = question.min; i <= question.max; i += question.step) {
        optionsHTML += `<option value="${i}" label="${i}"></option>`;
    }
    questionDiv.innerHTML = `
    <style>
        datalist {
            display: flex;
            flex-direction: row;
            justify-content: space-between;
            writing-mode: horizontal-tb;
            width: 525px;
        }

        input[type="range"] {
            width: 500px;
            margin: 0;
        }
    </style>
        <div class="card-body">
            <label for="range_q">${question.questionName}</label><br/>
            <input id="rangeInput" type="range" name="range_q" min="${question.min}" max="${question.max}" list="markers" step="${question.step}"/>
        </div>
        <datalist id="markers">
              ${optionsHTML}
        </datalist>
    `;
    return questionDiv;
}


const submitAnswerButton = document.getElementById("submitAnswerButton");
const questionId = document.getElementById("questionId");

async function saveAnswer() {
    const currentQuestion = questions[currentQuestionIndex];
    let answer = null;
    let range = null;
    let options = null;

    switch (currentQuestion.questionType) {
        case "OPEN":
            answer = document.getElementById("openQuestionInput").value;
            break;
        case "CHOICE":
            // if (currentQuestion.multiChoice) {
            //     options = document.querySelector("input[name='options_" + currentQuestion.id + "']:checked")?.value || null;
            // } else {
            //     const selectedOptions = document.querySelectorAll("input[name='options_" + currentQuestion.id + "']:checked");
            //     options = Array.from(selectedOptions).map(input => input.value);
            // }
            // if (currentQuestion.multiChoice) {
            //     const selectedOptionInputs = document.querySelectorAll("input[name='options_" + currentQuestion.id + "']:checked");
            //     options = Array.from(selectedOptionInputs).map(input => input.getAttribute('data-option-id'));
            // } else {
            const selectedOptionInput = document.querySelector("input[name='options_" + currentQuestion.id + "']:checked");
            options = selectedOptionInput ? [selectedOptionInput.getAttribute('data-option-id')] : [];
            // }
            break;
        case "RANGE":
            range = document.getElementById("rangeInput").value;
            break;
        default:
            console.error("Unsupported question type:", currentQuestion.questionType);
            return;
    }

    const answerData = {
        answer: currentQuestion.questionType === 'OPEN' ? answer : null,
        options_answer: currentQuestion.questionType === 'CHOICE' ? options : null,
        range_answer: currentQuestion.questionType === 'RANGE' ? parseInt(range) : null,
        questionId: currentQuestion.id,
        surveyId: surveyIdInput.value,
        answerType: currentQuestion.questionType,
    };

    const response = await fetch(`/api/answers/${currentQuestion.id}`, {
        method: 'POST',
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json",
            [header]: token
        },
        body: JSON.stringify(answerData)
    });

    if (response.status === 201) {
        const savedAnswer = await response.json();
        console.log("Successfully saved answer:", savedAnswer);
    } else {
        console.error("Failed to save answer:", response.status);
        alert("Failed to save answer. Please try again.");
    }
}


function displayQuestion(question) {
    questionsContainer.innerHTML = '';
    let questionElement;
    switch (question.questionType) {
        case "CHOICE":
            if (question.multiChoice) {
                questionElement = createChoiceQuestion(question, true);
            } else {
                questionElement = createChoiceQuestion(question, false);
            }
            break;
        case "OPEN":
            questionElement = createOpenQuestion(question);
            break;
        case "RANGE":
            questionElement = createRangeQuestion(question);
            break;
        default:
            questionElement = document.createElement('div');
            questionElement.textContent = 'Unsupported question type: ' + question.questionType;
    }
    questionsContainer.appendChild(questionElement);
}

submitAnswerButton?.addEventListener("click", async () => {
    await saveAnswer();
    await fetchNextQuestion();
});

window.addEventListener('load', fetchFirstQuestion);
