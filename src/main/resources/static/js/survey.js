const questionsContainer = document.getElementById("questionsContainer");
const nameContainer = document.getElementById("nameContainer");
const surveyIdInput = document.getElementById("surveyId");
const surveyNameInput = document.getElementById("surveyName");

nameContainer.innerHTML = surveyNameInput.value;

async function fetchQuestions() {
    const response = await fetch(`/api/surveys/${surveyIdInput.value}/questions`,
        {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        });
    if (response.status === 200) {
        const questions = await response.json();
        questionsContainer.innerHTML = '';
        for (const question of questions) {
            console.log(question.questionType);
            questionsContainer.innerHTML += `
            <label for="field">${question.questionName}</label>
            `;
        }
        displayQuestions(questions);
    }
}

function displayQuestions(questions) {
    questionsContainer.innerHTML = '';
    questions.forEach(question => {
        let questionElement;
        switch (question.questionType) {
            case "MULTIPLE_CHOICE":
                questionElement = createMultipleChoiceQuestion(question);
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
    });
}

function createOpenQuestion(question) {
    const questionDiv = document.createElement('div');
    questionDiv.classList.add('col-lg-10');
    questionDiv.innerHTML = `
        <div class="card-body">
            <h5 class="card-title">${question.questionName}</h5>
            <input type="text" class="form-control" id="openQuestionInput"">   
<!--             <input type="hidden" id="questionId" th:value="${questionId}">-->
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
            <select>
                ${question.options.map(option => `<option value="${option}">${option}</option>`).join('')}
            </select>
        </div>
    `;
    return questionDiv;
}

function createRangeQuestion(question) {
    const questionDiv = document.createElement('div');
    questionDiv.classList.add('col-lg-10');
    questionDiv.innerHTML = `
        <div class="card-body">
            <label>${question.questionName}</label>
            <input type="range" min="${question.min}" max="${question.max}" step="${question.step}">
        </div>
    `;
    return questionDiv;
}

window.addEventListener('load', fetchQuestions);

const submitAnswerButton = document.getElementById("submitAnswerButton");
const questionId = document.getElementById("questionId");

async function saveAnswer() {
    const openQuestionInput = document.getElementById("openQuestionInput");
    const response = await fetch(
        `/api/answers/open`, {
            method: 'POST',
            headers: {
                "Accept": "application/json",
                "Content-Type": "application/json"
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

submitAnswerButton?.addEventListener("click", saveAnswer);