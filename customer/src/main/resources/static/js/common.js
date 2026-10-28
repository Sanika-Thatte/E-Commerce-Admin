document.addEventListener("DOMContentLoaded", function () {

    // Image Preview
    window.previewImage = function(event) {
        const image = document.getElementById("preview");
        if (image && event.target.files.length > 0) {
            image.src = URL.createObjectURL(event.target.files[0]);
        }
    };

    // Image Modal
    window.openImage = function(src) {
        const modal = document.getElementById("imageModal");
        const img = document.getElementById("fullImage");

        if (modal && img) {
            modal.style.display = "block";
            img.src = src;
        }
    };

    window.closeImage = function() {
        const modal = document.getElementById("imageModal");
        if (modal) {
            modal.style.display = "none";
        }
    };

    // Logout
    window.confirmLogout = function() {
        if (confirm("Are you sure you want to logout?")) {
            const form = document.getElementById("logoutForm");
            if (form) {
                form.submit();
            }
        }
    };

    // Active Sidebar
    const currentPath = window.location.pathname;

    document.querySelectorAll(".sidebar a").forEach(link => {
        const linkPath = new URL(link.href).pathname;

        if (currentPath.startsWith(linkPath)) {
            link.classList.add("active");
        }
    });

});