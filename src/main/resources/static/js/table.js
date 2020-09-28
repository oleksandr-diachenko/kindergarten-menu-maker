$(document).ready(function() {
    $('table thead th').each(function(i) {
        calculateColumn(i);
    });
});

function calculateColumn(index) {
    var total = 0;
    $('table tr').each(function() {
        var value = parseFloat($('td', this).eq(index).text());
        if (!isNaN(value)) {
            total += value;
        }
    });
    $('table tfoot td').eq(index).text(total.toFixed(2));
}