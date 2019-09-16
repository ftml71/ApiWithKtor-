<#macro page>
    <!doctype html>
    <html lang="en">
    <head>
        <title>Movies</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    </head>
    <body>
    <#include "navbar.ftl">
    <div class="container-fluid">
        <#nested>
        <#include "footer.ftl">

    </div>
    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"></script>

    </body>

    </html>
</#macro>