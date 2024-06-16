import {header, token} from "./util/csrf.js";

const userTypeElement = document.getElementById('userType');
let userType = '';
if (userTypeElement) {
    userType = userTypeElement.value.toString();
} else {
    console.log('User is not logged in');
}

const addButton = document.getElementById('addButton');
const supervisorButton = document.getElementById('supervisorButton');
if (userType !== 'P_ADMIN') {
    addButton.hidden = true;
}
if (userType !== 'SUPERVISOR') {
    supervisorButton.hidden = true;
}


async function getSurveys() {
    const response = await fetch('/api/surveys', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            [header]: token
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
            const buttonContainer = document.createElement('div');
            buttonContainer.classList.add('button-container');

            const viewDetailsButton = document.createElement('div');
            viewDetailsButton.hidden = userType !== 'P_ADMIN';
            viewDetailsButton.classList.add('btn', 'btn-primary', 'button-spacing');
            viewDetailsButton.innerText = 'View Details';
            viewDetailsButton.addEventListener('click', () => {
                window.location.href = `/surveys/${survey.surveyId}/details`;
            });

            const exportDataCsvDataButton = document.createElement('div');
            exportDataCsvDataButton.hidden = userType !== 'P_ADMIN';
            exportDataCsvDataButton.classList.add('btn', 'btn-primary', 'button-spacing');
            exportDataCsvDataButton.innerText = 'Export CSV';
            exportDataCsvDataButton.addEventListener('click', async () => {
                const response = await fetch(`/api/answers/${survey.surveyId}/export-csv`, {
                    method: 'GET',
                    headers: {
                        'Accept': 'text/csv',
                        'Content-Type': 'text/csv',
                        [header]: token
                    },

                });

                if (response.status === 200) {
                    const csvData = await response.blob();
                    const url = window.URL.createObjectURL(csvData);
                    const a = document.createElement('a');
                    a.href = url;
                    a.download = 'survey_answers.csv';
                    document.body.appendChild(a);
                    a.click();
                    window.URL.revokeObjectURL(url);
                } else if (response.status === 204) {

                }
            });

            const statisticsButton = document.createElement('div');
            statisticsButton.hidden = userType !== 'P_ADMIN';
            statisticsButton.classList.add('btn', 'btn-primary', 'button-spacing');
            statisticsButton.innerText = 'View Statistics';
            statisticsButton.addEventListener('click', () => {
                window.location.href = `/surveys/${survey.surveyId}/statistics`;
            });
            buttonContainer.appendChild(viewDetailsButton);
            buttonContainer.appendChild(exportDataCsvDataButton);
            buttonContainer.appendChild(statisticsButton);
            document.getElementById('buttonContainer').appendChild(buttonContainer);

            innerDiv.appendChild(titleElement);
            innerDiv.appendChild(descriptionElement);
            innerDiv.appendChild(timeElement);

            surveyItem.appendChild(innerDiv);
            surveyItem.appendChild(viewDetailsButton);
            surveyItem.appendChild(exportDataCsvDataButton);
            surveyItem.appendChild(statisticsButton);
            surveysList.appendChild(surveyItem);

        });
    }
}

window.addEventListener('load', () => getSurveys());
