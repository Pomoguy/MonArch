<#import "auth.ftl" as a>
<#import "common.ftl" as com>
<#include "security.ftl">

<#macro nav>
    <@com.page>
        <nav class="navbar navbar-expand-lg navbar-dark" style="background-color:#960b10;">
            <a class="navbar-brand" href="/main">MonArch</a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
                    aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav mr-auto">
                    <li class="nav-item">
                        <a class="nav-link" href="/main">Главная</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/home">Демо_Сообщения</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/user">Демо_Пользователи</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#">Архитектурный комитет</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#">Регистрация сервиса</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#">Реестр систем</a>
                    </li>
                </ul>

                <div class="navbar-nav">
                    <#if known>
                        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdownMenuLink" role="button"
                           data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            Здравствуйте, ${name}
                        </a>
                        <div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
                            <@a.logout/>
                        </div>
                        <#else>
                            <li class="nav-item">
                                <a class="nav-link" href="/login">Вход</a>
                            </li>
                    </#if>
                </div>
            </div>
        </nav>
        <div class="container mt-3">
            <#nested>
        </div>
    </@com.page>
</#macro>