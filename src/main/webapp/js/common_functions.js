function confirmChoice(message, link)
{
    var r=confirm(message);
    if (r==true)
    {
        window.open(link, "_self");
    }
}

function confirmChoiceAndReload(message, linkOk)
{
    var r=confirm(message);
    if (r==true) {
        window.open(linkOk, "_self");
    } else {
        window.location.reload(true);
    }
}

function showAlert(message) {
    alert (message);
}

/*menu handler*/
$(function(){
    function stripTrailingSlash(str) {
        if(str.substr(-1) == '/') {
            return str.substr(0, str.length - 1);
        }
        return str;
    }

    var url = window.location.pathname;
    var activePage = stripTrailingSlash(url);

    $('.nav li a').each(function(){
        var currentPage = stripTrailingSlash($(this).attr('href'));

        if (activePage == currentPage) {
            $(this).parent().addClass('active');
        }
    });
});

/*Ajax call function */
function ajaxAsyncRequest(reqURL)
{
    //Creating a new XMLHttpRequest object
    var xmlhttp;
    if (window.XMLHttpRequest){
        xmlhttp = new XMLHttpRequest(); //for IE7+, Firefox, Chrome, Opera, Safari
    } else {
        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP"); //for IE6, IE5
    }
    //Create a asynchronous GET request
    xmlhttp.open("GET", reqURL, true);

    //When readyState is 4 then get the server output
    xmlhttp.onreadystatechange = function() {
        if (xmlhttp.readyState == 4) {
            if (xmlhttp.status == 200)
            {
                document.getElementById("message").innerHTML = xmlhttp.responseText;
                //alert(xmlhttp.responseText);
            }
            else
            {
                alert('Something is wrong !!');
            }
        }
    };

    xmlhttp.send(null);
}