// dropdown.js

document.addEventListener('DOMContentLoaded', function() {
    // Encuentra todos los dropdowns en la página
    var dropdowns = document.querySelectorAll('.dropdown-toggle');

    // Activa el dropdown para cada elemento encontrado
    dropdowns.forEach(function(dropdown) {
        dropdown.addEventListener('click', function() {
            dropdown.nextElementSibling.classList.toggle('show');
        });
    });

    // Cierra el dropdown cuando el usuario hace clic fuera de él
    window.addEventListener('click', function(event) {
        dropdowns.forEach(function(dropdown) {
            if (!dropdown.contains(event.target)) {
                dropdown.nextElementSibling.classList.remove('show');
            }
        });
    });
});
