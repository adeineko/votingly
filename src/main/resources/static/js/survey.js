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
                'Content-Type': 'application/json'
            }
        });
    if (response.status === 200) {
        questions = await response.json();
        // Display the first question
        displayQuestion(questions[currentQuestionIndex]);
    }
}

// async function fetchQuestions() {
//     const response = await fetch(`/api/surveys/${surveyIdInput.value}/questions`,
//         {
//             method: 'GET',
//             headers: {
//                 'Content-Type': 'application/json'
//             }
//         });
//     if (response.status === 200) {
//         questions = await response.json();
//         questionsContainer.innerHTML = '';
//         for (const question of questions) {
//             questionsContainer.innerHTML += `
//             <label for="field">${question.questionName}</label>
//             `;
//         }
//         displayQuestions(questions);
//     }
// }
async function fetchNextQuestion() {
    currentQuestionIndex++;
    // Fetch the next question from the server
    if (currentQuestionIndex < questions.length) {
        // If there are more questions, fetch the next one
        displayQuestion(questions[currentQuestionIndex]);
    } else {
        // If there are no more questions, redirect to thank-you page
        window.location.href = `/thank-you-page`;
    }
}


// function displayQuestions(questions) {
//     questionsContainer.innerHTML = '';
//     questions.forEach(question => {
//         let questionElement;
//         switch (question.questionType) {
//             case "MULTIPLE_CHOICE":
//                 questionElement = createMultipleChoiceQuestion(question);
//                 break;
//             case "OPEN":
//                 questionElement = createOpenQuestion(question);
//                 break;
//             case "RANGE":
//                 questionElement = createRangeQuestion(question);
//                 break;
//             default:
//                 questionElement = document.createElement('div');
//                 questionElement.textContent = 'Unsupported question type: ' + question.questionType;
//         }
//         questionsContainer.appendChild(questionElement);
//     });
// }

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

// window.addEventListener('load', fetchQuestions);

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

function displayQuestion(question) {
    // Clear the questions container
    questionsContainer.innerHTML = '';
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

// async function saveAnswer() {
//     const openQuestionInput = document.getElementById("openQuestionInput");
//     const response = await fetch(
//         `/api/answers/open`, {
//             method: 'POST',
//             headers: {
//                 "Accept": "application/json",
//                 "Content-Type": "application/json"
//             },
//             body: JSON.stringify(
//                 {
//                     answer: openQuestionInput.value,
//                     questionId: {
//                         id: questionId.value
//                     },
//                     surveyId: surveyIdInput.value,
//                     userId: 1
//                 }
//             )
//         }
//     );
//     if (response.status === 201) {
//         const answer = await response.json();
//         console.log("Successfully saved answer")
//         console.log(answer)
//         questions = questions.filter(question => question.id === questionId.value);
//
//         // Display the remaining questions
//         displayQuestions(questions);
//         console.log(questions.length)
//         // Check if there are no more questions
//         if (questions.length === 0) {
//             window.location.href = `/thank-you-page`;
//         }
//     } else {
//         alert("Something went wrong!");
//     }
// }
//
// submitAnswerButton?.addEventListener("click", saveAnswer);