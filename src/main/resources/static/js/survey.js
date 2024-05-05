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
        console.log("First question:", questions[currentQuestionIndex]); // Debugging line
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

        // window.location.href = `/thank-you-page`;
    }
}

function createOpenQuestion(question) {
    const questionDiv = document.createElement('div');
    questionDiv.classList.add('col-lg-10');
    questionDiv.innerHTML = `
        <div class="card-body">
            <h5 class="card-title">${question.questionName}</h5>
            <input type="text" class="form-control" id="openQuestionInput"">   
        </div>
    `;
    return questionDiv;
}

function createMultipleChoiceQuestion(question) {
    const questionDiv = document.createElement('div');
    questionDiv.classList.add('col-lg-10');
    questionDiv.innerHTML = `
        <div class="card-body">
            <label>${question.questionName}</label>
            ${question.options.map(option => `
                <div>
                    <input type="checkbox" id="multichoiceInput" name="${option}" value="${option}">
                    <label for="choiceInput">${option.optionText}</label>
                </div>
            `).join('')}
        </div>
        
    `;
    return questionDiv;
}

function createSingleChoiceQuestion(question) {
    const questionDiv = document.createElement('div');
    questionDiv.classList.add('col-lg-10');
    questionDiv.innerHTML = `
    <div class="card-body">
        <label>${question.questionName}</label>
        ${question.options.map(option => `
            <div>
                <input type="radio" id="choiceInput" name="options_${question.id}" value="${option}">
                <label for="choiceInput">${option.optionText}</label>
            </div>
        `).join('')}
    </div>
    `;
    return questionDiv;
}


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
            console.log(answer);
            console.log(typeof answer);
            break;
        case "CHOICE":
            // Retrieve selected choice inputs
            const multichoiceInputs = document.getElementById("multichoiceInput");
            const choiceInputs = document.getElementById("choiceInput");
            // console.log("Selected choice inputs:", choiceInputs);
            if (!currentQuestion.multiChoice) {
                console.log("Single choice selected:", choiceInputs.length > 0 ? choiceInputs[0].value : null);
                answer = document.querySelector("input[name='options_" + currentQuestion.id + "']:checked")?.value || null;
                console.log(answer);
                console.log(typeof answer);
            } else {
                console.log("Multiple choices selected:", Array.from(multichoiceInputs).map(input => input.value));
                options = Array.from(multichoiceInputs).map(input => input.value);
                console.log(options);
                console.log(typeof options);
            }

            break;
        case "RANGE":
            // Retrieve selected range input value
            answer = document.getElementById("rangeInput");
            // answer.toString()
            console.log(answer);
            console.log(typeof answer);
            break;
        default:
            console.error("Unsupported question type:", currentQuestion.questionType);
            return;
    }


    let answerData;
    if (currentQuestion.questionType === 'CHOICE') {
        if (currentQuestion.multiChoice) {
            answerData = {
                options: options, // if slider --> parseInt(answer)
                questionId: currentQuestion.id,
                surveyId: surveyIdInput.value // always parseInt
            };
        } else {
            answerData = {
                answer: answer, // if slider --> parseInt(answer)
                questionId: currentQuestion.id,
                surveyId: surveyIdInput.value // always parseInt
            };
        }
    } else if (currentQuestion.questionType === 'OPEN') {
        answerData = {
            answer: answer,
            questionId: currentQuestion.id,
            surveyId: surveyIdInput.value // always parseInt
        };
    } else {
        answerData = {
            number: answer.value,
            questionId: currentQuestion.id,
            surveyId: surveyIdInput.value // always parseInt
        };
    }

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
