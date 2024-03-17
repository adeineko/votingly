const surveyIdInput = document.getElementById("surveyId");
const questionsContainer = document.getElementById("questionsContainer");
const questionAnswersContainer = document.getElementById("questionAnswersContainer");

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

window.addEventListener('load', () => fetchQuestions());

function displayQuestions(questions) {
    questions.forEach(question => {
        let questionElement;
        switch (question.questionType) {
            case "MULTIPLE_CHOICE":
                questionElement = createMultipleChoiceQuestion();
                console.log(questionElement);
                break;
            case "OPEN":
                questionElement = createOpenQuestion();
                console.log(questionElement);
                break;
            case "RANGE":
                questionElement = createRangeQuestion();
                console.log(questionElement);
                break;
            default:
                questionElement = document.createElement('div');
                questionElement.textContent = 'Unsupported question type: ' + question.type;
        }
        questionsContainer.appendChild(questionElement);
    });
}

function createOpenQuestion() {
    const questionDiv = document.createElement('div');
    questionDiv.innerHTML += `
        <input type="text"  id="field">
    `;
    return questionDiv;
}

function createMultipleChoiceQuestion() {
    const questionDiv = document.createElement('div');
    questionDiv.classList.add('question');
    return questionDiv;

}

function createRangeQuestion() {
    const questionDiv = document.createElement('div');
    questionDiv.classList.add('question');
    return questionDiv;
}