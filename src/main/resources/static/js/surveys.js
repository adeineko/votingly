async function getSurveys() {
    const response = await fetch('/api/surveys', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    });

    if (response.status === 200) {
        const data = await response.json();
        const surveysList = document.getElementById('surveys-list');
        data.forEach(survey => {
            const surveyItem = document.createElement('div');
            surveyItem.classList.add('col');

            const innerDiv = document.createElement('div');
            innerDiv.classList.add('survey-item');

            const titleElement = document.createElement('h3');
            titleElement.innerText = survey.surveyName;

            const descriptionElement = document.createElement('p');
            // descriptionElement.innerText = survey.description;
            descriptionElement.innerText = 'Description: This survey is designed to gather information about the current state of the company.';

            const timeElement = document.createElement('p');
            // timeElement.innerText = `Estimated Time: ${survey.estimatedTime} mins`;
            timeElement.innerText = `Estimated Time: 10 minutes`;

            innerDiv.addEventListener('click', () => {
                window.location.href = `/surveys/${survey.surveyId}/questions`;
            });

            innerDiv.appendChild(titleElement);
            innerDiv.appendChild(descriptionElement);
            innerDiv.appendChild(timeElement);

            surveyItem.appendChild(innerDiv);
            surveysList.appendChild(surveyItem);
        });
    }
}

window.addEventListener('load', () => getSurveys());
