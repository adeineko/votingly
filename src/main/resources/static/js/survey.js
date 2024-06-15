import { header, token } from "./util/csrf.js";

const questionsContainer = document.getElementById("questionsContainer");
const nameContainer = document.getElementById("nameContainer");
const surveyIdInput = document.getElementById("surveyId");
const surveyNameInput = document.getElementById("surveyName");
const surveyTypeInput = document.getElementById("surveyType");
const pauseButton = document.getElementById("pauseButton");
const resumeButton = document.getElementById("resumeButton");
const progressBarContainer = document.getElementById("progress-bar-container");
resumeButton.hidden = true;

if (surveyTypeInput.value === "LINEAR") {
    pauseButton.hidden = true;
    resumeButton.hidden = true;
    progressBarContainer.hidden = true;
}

let currentQuestionIndex = 0;
let questions = [];
let circularFlowInterval;
let isPaused = false;

nameContainer.innerHTML = surveyNameInput.value;

async function fetchFirstQuestion() {
    const response = await fetch(
        `/api/surveys/${surveyIdInput.value}/questions`,
        {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
                [header]: token,
            },
        }
    );
    if (response.status === 200) {
        questions = await response.json();
        displayQuestion(questions[currentQuestionIndex]);
        if (surveyTypeInput.value === "CIRCULAR") {
            startCircularFlow();
            resetProgressBar(); // Start the progress bar immediately
        }
    }
}

function fetchNextQuestion() {
    currentQuestionIndex = (currentQuestionIndex + 1) % questions.length;
    displayQuestion(questions[currentQuestionIndex]);
    resetProgressBar(); // Reset progress bar on fetching next question
    restartCircularFlow(); // Restart the circular flow on fetching next question
}

function startCircularFlow() {
    circularFlowInterval = setInterval(() => {
        if (!isPaused) {
            fetchNextQuestion();
        }
    }, 8000);
}

function restartCircularFlow() {
    clearInterval(circularFlowInterval);
    if (!isPaused) {
        startCircularFlow();
    }
}

let progressBarWidth = 0; // Variable to store the current width of the progress bar

function pauseCircularFlow() {
    isPaused = true;
    clearInterval(circularFlowInterval); // Stop the circular flow when paused
    progressBarWidth = getCurrentProgressBarWidth(); // Store the current width
    pauseTransition(); // Pause the progress bar transition
    resumeButton.hidden = false;
    pauseButton.hidden = true;
}

function resumeCircularFlow() {
    isPaused = false;
    startCircularFlow(); // Resume circular flow when resumed
    resumeTransition(); // Resume the progress bar transition
    resumeButton.hidden = true;
    pauseButton.hidden = false;
}

function pauseTransition() {
    const progressBar = document.getElementById("progress-bar");
    progressBar.style.transition = "none"; // Pause the transition by setting it to 'none'
    progressBar.style.width = `${progressBarWidth}px`; // Set the width to the current width
}

function resumeTransition() {
    const progressBar = document.getElementById("progress-bar");
    progressBar.style.transition = "width 8s linear"; // Resume the transition
    progressBar.style.width = "100%"; // Reset the width to 100% to resume the animation
}

function getCurrentProgressBarWidth() {
    const progressBar = document.getElementById("progress-bar");
    return progressBar.offsetWidth; // Return the current width of the progress bar
}

function resetProgressBar() {
    const progressBar = document.getElementById("progress-bar");
    progressBar.style.transition = "none";
    progressBar.style.width = "0";
    setTimeout(() => {
        progressBar.style.transition = "width 8s linear";
        progressBar.style.width = "100%";
    }, 10); // Small delay to allow the DOM to update
}

function createOpenQuestion(question) {
    const questionDiv = document.createElement("div");
    questionDiv.classList.add("col-lg-10");

    const cardBody = document.createElement("div");
    cardBody.classList.add("card-body");
    questionDiv.appendChild(cardBody);

    const cardTitle = document.createElement("h5");
    cardTitle.classList.add("card-title");
    cardTitle.textContent = question.questionName;
    cardBody.appendChild(cardTitle);

    const input = document.createElement("input");
    input.setAttribute("type", "text");
    input.classList.add("form-control");
    input.setAttribute("id", "openQuestionInput");
    cardBody.appendChild(input);

    return questionDiv;
}

function createChoiceQuestion(question, isMultiChoice) {
    const questionDiv = document.createElement("div");
    questionDiv.classList.add("col-lg-10");

    const cardBody = document.createElement("div");
    cardBody.classList.add("card-body");
    questionDiv.appendChild(cardBody);

    const cardTitle = document.createElement("h5");
    cardTitle.classList.add("card-title");
    cardTitle.textContent = question.questionName;
    cardBody.appendChild(cardTitle);

    question.options.forEach((option) => {
        const optionDiv = document.createElement("div");

        const input = document.createElement("input");

        input.setAttribute("type", isMultiChoice ? "checkbox" : "radio");
        // input.setAttribute('name', isMultiChoice ? `options_${question.id}[]` : `options_${question.id}`);
        input.setAttribute("name", `options_${question.id}`);
        input.setAttribute("value", option.optionText);
        input.setAttribute("data-option-id", option.optionId);

        const optionLabel = document.createElement("label");
        optionLabel.textContent = option.optionText;

        optionDiv.appendChild(input);
        optionDiv.appendChild(optionLabel);

        cardBody.appendChild(optionDiv);
    });

    return questionDiv;
}

export function createRangeQuestion(question) {
    const questionDiv = document.createElement("div");
    questionDiv.classList.add("col-lg-10");
    let optionsHTML = "";
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
            const selectedOptions = document.querySelectorAll(
                "input[name='options_" + currentQuestion.id + "']:checked"
            );
            // console.log("Selected options:", selectedOptions);
            options = Array.from(selectedOptions).map((input) =>
                parseInt(input.getAttribute("data-option-id"))
            );
            // console.log("Selected options:", options);
            break;
        case "RANGE":
            range = document.getElementById("rangeInput").value;
            break;
        default:
            console.error(
                "Unsupported question type:",
                currentQuestion.questionType
            );
            return;
    }

    const answerData = {
        surveyId: surveyIdInput.value,
        questionId: currentQuestion.id,
        // Answer to Open question
        // String
        answer: currentQuestion.questionType === "OPEN" ? answer : null,
        // Answer to Choice and Multichoice question
        // float[] - array of selected option ids
        options_answer:
            currentQuestion.questionType === "CHOICE" ? options : null,
        // Answer to Range question
        // int - selected range value
        range_answer:
            currentQuestion.questionType === "RANGE" ? parseInt(range) : null,
    };

    const response = await fetch(`/api/answers/${currentQuestion.id}`, {
        method: "POST",
        headers: {
            Accept: "application/json",
            "Content-Type": "application/json",
            [header]: token,
        },
        body: JSON.stringify(answerData),
    });

    if (response.status === 201) {
        // const savedAnswer = await response.json();
        // console.log("Successfully saved answer:", savedAnswer);
    } else {
        console.error("Failed to save answer:", response.status);
        alert("Failed to save answer. Please try again.");
    }
}

function displayQuestion(question) {
    questionsContainer.innerHTML = "";
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
            questionElement = document.createElement("div");
            questionElement.textContent =
                "Unsupported question type: " + question.questionType;
    }
    questionsContainer.appendChild(questionElement);
}

submitAnswerButton?.addEventListener("click", async () => {
    await saveAnswer();
    fetchNextQuestion();
});

pauseButton?.addEventListener("click", pauseCircularFlow);
resumeButton?.addEventListener("click", resumeCircularFlow);

window.addEventListener("load", fetchFirstQuestion);

const surveyURL = `https://votingly.tech/surveys/${surveyIdInput.value}/questions`;
new QRCode(document.getElementById("qrcode"), surveyURL);
