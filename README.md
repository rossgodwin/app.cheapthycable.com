
## Deploy

Change to WebContent\client\build directory
npm install
grunt build-app
grunt prod-clean

## Change Context Path

### No Context Path

Update \WebContent\client\src\app\res\AppConsts.js - change contextPath variable to empty string - ''
Update \WebContent\WEB-INF\jboss-web.xml - change context-root variable to just forward slash - <context-root>/</context-root>

## DNS Provider

Namecheap.com

## Email

### Host

Mailgun.org

## Server

### Rewrite

Servlet extension
http://www.ocpsoft.org/rewrite

### Email templating

Thymeleaf.org
WebContent\WEB-INF\templates\emails

## Client

### Icons
https://icomoon.io/app

### Resources
https://cdnjs.com/libraries

#### angular-spinners
http://codetunnel.io/how-to-do-loading-spinners-the-angular-way/
https://github.com/Chevtek/angular-spinners
https://github.com/Chevtek/angular-spinners#frequently-asked-questions


#### ng-currency
https://github.com/aguirrel/ng-currency


#### ui-bootstrap
http://angular-ui.github.io/bootstrap/

#### angular-easyfb
https://github.com/pc035860/angular-easyfb

#### AngularPlus
https://github.com/AngularPlus/AngularPlus

## Helpful Angular Resources

http://ngbp.github.io/ngbp/#/home
https://github.com/johnpapa/HotTowel-Angular
https://github.com/johnpapa/generator-hottowel
https://github.com/ThomasBurleson/angularjs-Quizzler

