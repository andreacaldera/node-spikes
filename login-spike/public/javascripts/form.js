$("#imageInput").on('change', function() {
    var image = $("#image");
    image.show();
    image.text($(this).val().replace(/\\/g, '/').replace(/.*\//, ''));
});