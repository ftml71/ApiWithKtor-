<nav class="navbar navbar-default">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                    data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="/">Movies</a>
        </div>
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <li><a href="/">Home</a></li>
                <li><a href="/about">about</a></li>
                <li><a href="/movies">Movies</a></li>
            </ul>

            <ul class="nav navbar-nav navbar-right">
                <#if user??>

                    <li><p class="navbar-text">welcome ${user.displayName}</p></li>
                    <li><a href="/signout">Sign Out</a></li>
                <#else>
                    <li><a href="/signin">Sign In</a></li>
                    <li><a href="/signup">Sign Up</a></li>
                </#if>
            </ul>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
</nav>