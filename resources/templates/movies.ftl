<#import "common/bootstrap.ftl" as b>

<@b.page>
    <#if movies?? && (movies?size >0 )>
        <table class="table table-striped">
            <thead>
            <tr>
                <th>Title</th>
                <th>Description</th>
            </tr>
            </thead>
            <tbody>
            <#list movies as movie>
                <tr>
                    <td style="vertical-align: middle"><h3>${movie.post_title}</h3></td>
                    <td style="vertical-align: middle"><h3>${movie.post_description}</h3></td>
                    <td class="col-md-1" style="text-align: center;vertical-align: middle">
                        <form method="post" action="/movies">
                            <input type="hidden" name="id" value="${movie.id}">
                            <input type="hidden" name="action" value="delete">
                            <input type="image" src="/static/delete.png" width="24" height="24" border="0" alt="Delete" />
                        </form>
                    </td>
                </tr>
            </#list>
            </tbody>
        </table>
    </#if>
    <div class="panel-body">
        <form method="post" action="/movies">
            <input type="hidden" name="action" value="add" >
            Title:<br>
            <input type="text" name="post_title"/><br>
            Description:<br>
            <input type="text" name="post_description"/><br>
            <input type="submit" valus="submit"/>
        </form>
    </div>
</@b.page>