// fetch.js (JavaScript 파일)
document.addEventListener('DOMContentLoaded', function() {
    // 서버로부터 데이터를 받아오는 함수
    fetch('/api/restaurant')
        .then(response => {
            // 응답이 성공적이면 JSON으로 파싱
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            // 받은 데이터 처리 (예: HTML에 동적으로 추가)
            const restaurantList = document.getElementById('restaurant-list');
            data.forEach(restaurant => {
                const li = document.createElement('li');
                li.textContent = `${restaurant.name} - ${restaurant.address}`;
                restaurantList.appendChild(li);
            });
        })
        .catch(error => {
            console.error('There was a problem with the fetch operation:', error);
        });
});
