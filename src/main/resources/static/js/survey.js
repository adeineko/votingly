const surveyIdInput = document.getElementById("surveyId");
const questionsContainer = document.getElementById("questionsContainer");

async function fetchQuestions() {
    console.log("fetching...")
    const response = await fetch(`/api/forms/${surveyIdInput.value}/questions`,
        {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        });
    if (response.status === 200) {
        console.log("fetched successfully");
        const questions = await response.json();
        questionsContainer.innerHTML = '';
        for (const question of questions) {
            questionsContainer.innerHTML += `
            <div>${question.questionName}</div>
            `;
        }
    }
}

window.addEventListener('load', () => fetchQuestions());

function displayQuestions(questions) {
    $("#questions").empty();
    questions.forEach(question => {
        let questionElement;
        switch (question.type) {
            case "multiple_choice":
                questionElement = createMultipleChoiceQuestion(question);
                break;
            case "open":
                questionElement = createOpenEndedQuestion(question);
                break;
            case "range":
                questionElement = createRangeQuestion(question);
                break;
            default:
                questionElement = $('<div>Unsupported question type: ' + question.type + '</div>');
        }
        $("#questions").append(questionElement);
    });
}

function createMultipleChoiceQuestion(question) {
    //logic
    return $('<div class="question"><h2>' + question.text + '</h2><ul></ul></div>');
}

function createOpenEndedQuestion(question) {
    //logic
    return $('<div class="question"><h2>' + question.text + '</h2><textarea rows="5"></textarea></div>');
}

function createRangeQuestion(question) {
    //logic
    return $('<div class="question"><h2>' + question.text + '</h2><input type="range" min="0" max="100"></div>');
}