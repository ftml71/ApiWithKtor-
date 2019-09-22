<#import "common/bootstrap.ftl" as layout />
<@layout.page>
    <form class="pure-form-stacked" action="/signup" method="post" enctype="application/x-www-form-urlencoded">
        <#if error??>
            <p class="error">${error}</p>
        </#if>

        <label for="userId">Login
            <input type="text" name="userId" id="userId" >
        </label>


        <label for="email">Mail
            <input type="email" name="email" id="email" >
        </label>


        <label for="displayName">Display name
            <input type="text" name="displayName" id="displayName" >
        </label>


        <label for="password">Password
            <input type="password" name="password" id="password">
        </label>


        <input class="pure-button pure-button-primary" type="submit" value="Register">
    </form>
</@layout.page>