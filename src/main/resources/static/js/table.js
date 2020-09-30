$(document).ready(function () {
    let ids = $('.table').map((i, el) => el.getAttribute('id')).get();

    $.each(ids, function (index, value) {
        $('#' + value).tableTotal({
            totalRow: true,
            totalCol: false,
            bold: false
        });
    });
});