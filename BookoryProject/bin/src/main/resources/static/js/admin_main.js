// src/main/resources/static/js/admin_main.js

document.addEventListener('DOMContentLoaded', function() {
    const sidebarToggle = document.getElementById('sidebarToggle');
    const adminSidebar = document.querySelector('.admin-sidebar');
    const adminWrapper = document.querySelector('.admin-wrapper');

    if (sidebarToggle && adminSidebar && adminWrapper) {
        sidebarToggle.addEventListener('click', function() {
            adminSidebar.classList.toggle('show'); // Toggle class 'show' cho sidebar
            adminWrapper.classList.toggle('overlay'); // Thêm overlay khi sidebar mở
        });

        // Đóng sidebar khi click ra ngoài overlay (chỉ trên mobile)
        adminWrapper.addEventListener('click', function(event) {
            if (adminWrapper.classList.contains('overlay') && !adminSidebar.contains(event.target) && !sidebarToggle.contains(event.target)) {
                adminSidebar.classList.remove('show');
                adminWrapper.classList.remove('overlay');
            }
        });
    }

    // Optional: Thêm chức năng thu gọn sidebar trên desktop (nếu muốn)
    // Ví dụ: khi click vào logo hoặc một nút khác
    // const desktopSidebarToggle = document.getElementById('desktopSidebarToggle');
    // if (desktopSidebarToggle && adminSidebar) {
    //     desktopSidebarToggle.addEventListener('click', function() {
    //         adminSidebar.classList.toggle('collapsed');
    //     });
    // }
});