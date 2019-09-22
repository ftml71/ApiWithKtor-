<#import "common/bootstrap.ftl" as b />
<@b.page>
    <div class="panel-body">
        <form method="post" action="/signin">
            userId:<br>
            <input type="text" name="userId"/><br>
            password:<br>
            <input type="text" name="password"/><br>
            <input type="submit" valus="submit"/>
        </form>
    </div>
</@b.page>
