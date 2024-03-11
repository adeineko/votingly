const surveyIdInput = document.getElementById("surveyId");

async function fetchQuestions(surveyId) {
    const response = await fetch(`/api/forms/${surveyIdInput.value}/questions`,
        {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        });
    if (response.status === 200) {
        const data = await response.json();
        data.forEach(question => {
            question.innerText = question.questionName;
        });

    }
}
window.addEventListener('load', () => fetchQuestions(surveyIdInput.value));

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
    const element = $('<div class="question"><h2>' + question.text + '</h2><ul></ul></div>');
    return element;
}

function createOpenEndedQuestion(question) {
    const element = $('<div class="question"><h2>' + question.text + '</h2><textarea rows="5"></textarea></div>');
    return element;
}

function createRangeQuestion(question) {
    const element = $('<div class="question"><h2>' + question.text + '</h2><input type="range" min="0" max="100"></div>');
    return element;
}