window.addEventListener("load", function () {
    initExchangeChart();
});

function initExchangeChart() {
    const canvas = document.getElementById("exchangeRates");

    if (!canvas) return;

    const labels =
        canvas
            .getAttribute("data-labels")
            .replace(/[\[\]\s]/g, '')
            .split(",")
            .reverse();

    const values =
        canvas
            .getAttribute("data-values")
            .replace(/[\[\]\s]/g, '')
            .split(",")
            .map(Number)
            .reverse();

    new Chart(canvas, {
        type: 'line',
        data: {
            labels: labels,
            datasets: [{
                label: 'Ft',
                data: values,
                borderColor: '#24acd0',
                backgroundColor: 'rgba(22,213,255,0.5)',
                tension: 0.3,
                fill: true
            }]
        },
        options: {
            responsive: true,
            plugins: {
                title: {
                    display: true,
                    text: 'Napi valutaárfolyam'
                },
                legend: {display: false}
            },
            interaction: {
                intersect: false,
            },
            scales: {
                x: {
                    display: true,
                    title: {
                        display: true,
                        text: 'Dátum'
                    }
                },
                y: {
                    display: true,
                    title: {
                        display: true,
                        text: 'Forint (HUF)'
                    }
                }
            }
        }
    });
}