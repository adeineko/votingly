import {token, header} from "./util/csrf.js";

const surveyIdInput = document.getElementById("surveyId");

async function fetchDataAndRender() {
    try {
        const response = await fetch(`/api/answers/${surveyIdInput.value}/statistics`, {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                [header]: token
            }
        });

        if (response.status === 200) {
            const data = await response.json();
            renderChart(data);
        } else {
            console.error('Failed to fetch data');
        }
    } catch (error) {
        console.error('Error fetching data:', error);
    }
}

function renderChart(data) {
    const labels = data.map(item => item.answerTime);
    const values = data.map(item => item.answerCount);

    const ctx = document.getElementById('myChart').getContext('2d');
    const myChart = new Chart(ctx, {
        type: 'line',
        data: {
            labels: labels,
            datasets: [{
                label: 'Count of Answers',
                data: values,
                backgroundColor: 'rgba(75, 192, 192, 0.2)',
                borderColor: 'rgba(75, 192, 192, 1)',
                borderWidth: 1,
                fill: false
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            scales: {
                x: {
                    type: 'time',
                    time: {
                        unit: 'day'
                    },
                    title: {
                        display: true,
                        text: 'Date'
                    }
                },
                y: {
                    beginAtZero: true,
                    title: {
                        display: true,
                        text: 'Count of Answers'
                    }
                }
            }
        }
    });
}

document.addEventListener('DOMContentLoaded', fetchDataAndRender);
