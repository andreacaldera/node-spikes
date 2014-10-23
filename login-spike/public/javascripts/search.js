$( document ).ready(function() {
    console.log( "ready!" );
    console.log($("#searchInput")).val();
    var parameters = { q_description: $(this).val() };
    $.get( '/searching',parameters, function(data) {
        $('#results').html(data);
    });
});