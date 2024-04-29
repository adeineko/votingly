let questionCount = 0;

for (let i = 0; i < 3; i++) {
    createQuestion()
}

function createQuestion() {
    questionCount++;
    const questionDiv = document.createElement("div");
    questionDiv.setAttribute('id', `questionBlock${questionCount}`);
    questionDiv.classList.add("mb-3", "question-block");

    questionDiv.innerHTML = `
        <div class="d-flex justify-content-between align-items-center">
            <label for="question${questionCount}" class="form-label">Question ${questionCount}</label>
            <button type="button" class="btn btn-danger btn-sm" onclick="removeQuestion(${questionCount})">Remove Question</button>
        </div>
        <input type="text" class="form-control" id="question${questionCount}" name="questions[${questionCount}][text]" required>

        <select class="form-select mt-2" id="questionType${questionCount}" name="questions[${questionCount}][type]">
            <option value="open">Open</option>
            <option value="multipleChoice">Multiple Choice</option>
        </select>

        <div id="choicesContainer${questionCount}" class="choices-container mt-2"></div>
    `;

    document.getElementById("questionContainer").appendChild(questionDiv);

    // Handling type change
    const typeSelect = document.getElementById(`questionType${questionCount}`);
    typeSelect.addEventListener('change', () => handleTypeChange(questionCount));
}

function removeQuestion(questionNumber) {
    const questionDiv = document.getElementById(`questionBlock${questionNumber}`);
    questionDiv.parentNode.removeChild(questionDiv);
}

function handleTypeChange(questionNumber) {
    const typeValue = document.getElementById(`questionType${questionNumber}`).value;
    const choicesContainer = document.getElementById(`choicesContainer${questionNumber}`);
    choicesContainer.innerHTML = '';

    if (typeValue === 'multipleChoice') {
        const addChoiceBtn = document.createElement("button");
        addChoiceBtn.textContent = "Add Choice";
        addChoiceBtn.type = "button";
        addChoiceBtn.className = "btn btn-info btn-sm mt-2";
        addChoiceBtn.onclick = () => addChoiceInput(questionNumber);
        choicesContainer.appendChild(addChoiceBtn);
        addChoiceInput(questionNumber); // Add first choice input immediately
    }
}

function addChoiceInput(questionNumber) {
    const choiceContainer = document.createElement("div");
    choiceContainer.className = "input-group mb-2";

    const choiceInput = document.createElement("input");
    choiceInput.type = "text";
    choiceInput.className = "form-control";
    choiceInput.placeholder = "Enter choice";
    choiceInput.name = `questions[${questionNumber}][choices][]`;

    const removeChoiceBtn = document.createElement("button");
    removeChoiceBtn.type = "button";
    removeChoiceBtn.className = "btn btn-danger";
    removeChoiceBtn.textContent = "Remove";
    removeChoiceBtn.onclick = function() {
        choiceContainer.remove();
    };

    choiceContainer.appendChild(choiceInput);
    choiceContainer.appendChild(removeChoiceBtn);

    document.getElementById(`choicesContainer${questionNumber}`).appendChild(choiceContainer);
}

document.getElementById("addQuestionBtn").addEventListener("click", createQuestion);
document.getElementById("surveyForm").addEventListener("submit", submitSurvey);

function submitSurvey(event) {
    event.preventDefault();
    const formData = new FormData(event.target);
    const data = Object.fromEntries(formData.entries());

    fetch('/api/surveys', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
        .then(response => response.json())
        .then(data => {
            console.log('Success:', data);
            alert('Survey submitted successfully!');
        })
        .catch((error) => {
            console.error('Error:', error);
            alert('Error submitting survey.');
        });
}
