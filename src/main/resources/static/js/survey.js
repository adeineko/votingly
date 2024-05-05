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

function createMultipleChoiceQuestion(question) {
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
        input.setAttribute('type', 'checkbox');
        input.setAttribute('id', 'multichoiceInput');
        input.setAttribute('name', option);
        input.setAttribute('value', option.optionText);

        const optionLabel = document.createElement('label');
        optionLabel.setAttribute('for', 'multichoiceInput');
        optionLabel.textContent = option.optionText;

        optionDiv.appendChild(input);
        optionDiv.appendChild(optionLabel);

        cardBody.appendChild(optionDiv);
    });

    return questionDiv;
}

function createSingleChoiceQuestion(question) {
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
        input.setAttribute('type', 'radio');
        input.setAttribute('id', 'choiceInput');
        input.setAttribute('name', `options_${question.id}`);
        input.setAttribute('value', option.optionText);

        const optionLabel = document.createElement('label');
        optionLabel.setAttribute('for', 'choiceInput');
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

//TODO: fix questionId (for now it saves same questionId  for all questions)
async function saveAnswer() {
    const currentQuestion = questions[currentQuestionIndex];
    let answer;
    let options = [];

    switch (currentQuestion.questionType) {
        case "OPEN":
            answer = document.getElementById("openQuestionInput").value;
            break;
        case "CHOICE":
            const multichoiceInputs = document.querySelectorAll("#multichoiceInput input");
            if (!currentQuestion.multiChoice) {
                answer = document.querySelector("input[name='options_" + currentQuestion.id + "']:checked")?.value || null;
            } else {
                options = Array.from(multichoiceInputs).map(input => input.value);
            }
            break;
        case "RANGE":
            answer = document.getElementById("rangeInput").value;
            break;
        default:
            console.error("Unsupported question type:", currentQuestion.questionType);
            return;
    }

    const answerData = {
        answer: currentQuestion.questionType === 'OPEN' ? answer : (currentQuestion.questionType === 'CHOICE' && !currentQuestion.multiChoice ? answer : parseInt(answer)),
        options: currentQuestion.questionType === 'CHOICE' && currentQuestion.multiChoice ? options : null,
        range_answer: currentQuestion.questionType === 'RANGE' ? parseInt(answer) : null,
        questionId: currentQuestion.id,
        surveyId: parseInt(surveyIdInput.value)
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

    if (response.ok) {
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
            if (question.multiChoice === false) {
                console.log("single");
                questionElement = createSingleChoiceQuestion(question);
            } else if (question.multiChoice === true) {
                console.log("multi");
                questionElement = createMultipleChoiceQuestion(question);
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
