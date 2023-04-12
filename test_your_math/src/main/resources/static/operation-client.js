function updateMultiplication() {
    $.ajax({
        url: "http://localhost:8080/test-your-math/operations/random"
    }).then(function(data) {
        // Cleans the form
        $("#attempt-form").find( "input[name='attempt']" ).val("");
        $("#attempt-form").find( "input[name='alias']" ).val("");
        // Gets a random challenge from API and loads the data in the HTML
        $('.operation-a').empty().append(data.factorA);
        $('.operator').empty().append(data.operator);
        $('.operation-b').empty().append(data.factorB);
    });
}

function updateStats(alias) {
    $.ajax({
        url: "http://localhost:8080/test-your-math/results?alias=" + alias,
    }).then(function(data) {
        $('#stats-body').empty();
        data.forEach(function(row) {
            $('#stats-body').append('<tr><td style="text-align: center">' + row.id + '</td>' +
                '<td style="text-align: center">' + row.operation.factorA + ' '  + row.operation.operator +  ' ' + row.operation.factorB + '</td>' +
                '<td style="text-align: center">' + row.resultAttempt + '</td>' +

                '<td style="text-align: center">' + (row.correct === true ? 'YES' : 'NO') + '</td></tr>');
        });
    });
}

function updateLeaderboard() {
    $.ajax({
        url: "http://localhost:8080/test-your-math/results/leaderboard",
    }).then(function(data) {
        $('#leaderboard-body').empty();
        data.forEach(function(row) {
            $('#leaderboard-body').append('<tr><td style="text-align: center">' + '  ' + row.id + '  ' + '</td>' +
                '<td style="text-align: center">' + '    '+ row.alias + '    ' + '</td>' +
                '<td style="text-align: center">' + row.points + '</td>' +
                '<td style="text-align: center">' + row.medalii + '</td></tr>');
        });
    });
}

$(document).ready(function() {

    updateMultiplication();

    updateLeaderboard();

    $("#attempt-form").submit(function( event ) {

        // Don't submit the form normally
        event.preventDefault();

        // Get some values from elements on the page
        var a = $('.operation-a').text();
        var b = $('.operation-b').text();
         var c = $('.operator').text();
        var $form = $( this ),
            attempt = $form.find( "input[name='attempt']" ).val(),
            userAlias = $form.find( "input[name='alias']" ).val();

        // Compose the data in the format that the API is expecting
        var data = { user: { alias: userAlias}, operation: {factorA: a, factorB: b, operator: c}, resultAttempt: attempt};

        // Send the data using post
        $.ajax({
            url: '/test-your-math/results',
            type: 'POST',
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            async: false,
            success: function(result){
                if(result.correct) {
                    $('.result-message').empty().append("The result is correct! Congratulations!");
                } else {
                    $('.result-message').empty().append("Ooops that's not correct! But keep trying!");
                }
            }
        });

        updateMultiplication();

        updateStats(userAlias);

        updateLeaderboard();
    });
});