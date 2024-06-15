import { header, token } from "./util/csrf.js";

document.addEventListener("DOMContentLoaded", () => {
    const surveyNameInput = document.getElementById('surveyName');
    const surveyId = document.getElementById('surveyId').value;
    const otherSurveysList = document.getElementById('otherSurveys');

    console.log('Survey ID:', surveyId);

    async function fetchSurvey() {
        try {
            const response = await fetch(`/api/surveys/${surveyId}/details`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                    [header]: token
                }
            });
            if (response.ok) {
                const survey = await response.json();
                surveyNameInput.textContent = survey.surveyName;
            } else {
                console.error('Error fetching survey:', response.status);
            }
        } catch (error) {
            console.error('Error fetching survey:', error);
        }
    }

    async function getAllSurveys() {
        try {
            const response = await fetch('/api/surveys', {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                    [header]: token
                }
            });
            if (response.ok) {
                const surveys = await response.json();
                surveys.forEach(survey => {
                    const listItem = document.createElement('li');
                    listItem.classList.add('list-group-item');
                    listItem.innerText = survey.surveyName;
                    listItem.addEventListener('click', () => {
                        window.location.href = `/surveys/${survey.surveyId}/supervisor`;
                    });
                    otherSurveysList.appendChild(listItem);
                });
            } else {
                console.error('Error fetching surveys:', response.status);
            }
        } catch (error) {
            console.error('Error fetching surveys:', error);
        }
    }

    fetchSurvey();
    getAllSurveys();
});

const surveyId = document.getElementById('surveyId').value;

// Define saveNote globally
window.saveNote = async function() {
    try {
        console.log('Saving note');
        const note = document.getElementById("note").value;
        const response = await fetch(`/api/surveys/${surveyId}/supervisor`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                [header]: token
            },
            body: JSON.stringify({ content: note })
        });
        if (response.ok) {
            console.log('Note saved successfully');
            window.location.reload(); // Reload the page or update UI as needed
        } else {
            console.error('Error saving note:', response.status);
        }
    } catch (error) {
        console.error('Error saving note:', error);
    }
};
