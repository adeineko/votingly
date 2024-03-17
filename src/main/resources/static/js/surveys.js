const listElement = document.getElementById('surveys-list');

async function getSurveys() {
    const response = await fetch('/api/surveys',
        {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        });

    if (response.status === 200) {
        const data = await response.json();
        data.forEach(survey => {
            const surveyItem = document.createElement('li');
            surveyItem.innerText = survey.surveyName;
            console.log(survey.surveyName)
            surveyItem.addEventListener('click', () => {
                window.location.href = `/surveys/${survey.surveyId}/questions`;
            });
            listElement.appendChild(surveyItem);
        });

    }
}

window.addEventListener('load', () => getSurveys());
