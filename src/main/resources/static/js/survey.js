import { header, token } from "./util/csrf.js";

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
        window.location.href = `/thank-you-page`;
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
                    <input type="checkbox" id="${option}" name="${option}" value="${option}">
                    <label for="${option}">${option}</label>
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
                <input type="radio" id="${option}" name="options" value="${option}">
                <label for="${option}">${option}</label>
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
            <input type="range" name="range_q" min="${question.min}" max="${question.max}" list="markers" step="${question.step}"/>
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
    const openQuestionInput = document.getElementById("openQuestionInput");
    // const currentQuestion = questions[currentQuestionIndex];
    const response = await fetch(
        `/api/answers/open`, {
            method: 'POST',
            headers: {
                "Accept": "application/json",
                "Content-Type": "application/json",
                [header]: token
            },
            body: JSON.stringify(
                {
                    answer: openQuestionInput.value,
                    questionId: {
                        id: questionId.value
                    },
                    surveyId: surveyIdInput.value,
                    userId: 1
                }
            )
        }
    );
    if (response.status === 201) {
        const answer = await response.json();
        console.log("Successfully saved answer")
        console.log(answer)
    } else {
        alert("Something went wrong!");
    }
}

function displayQuestion(question) {
    // Clear the questions container
    questionsContainer.innerHTML = '';
    let questionElement;
    switch (question.questionType) {
        case "CHOICE":
            if(question.multiChoice === false){
                console.log("single");
                questionElement = createSingleChoiceQuestion(question);
            }else if(question.multiChoice === true){
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

// Event listener for submit answer button
submitAnswerButton?.addEventListener("click", async () => {
    // Save the answer
    await saveAnswer();
    // Fetch the next question
    await fetchNextQuestion();
});

// Initial fetch of the first question when the page loads
window.addEventListener('load', fetchFirstQuestion);
