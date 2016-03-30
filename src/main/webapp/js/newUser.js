/**
 * Created by Alina on 29.03.2016.
 */

function validName(){
    var name = document.forms['newUser']['name'].value;
    var login = document.forms['newUser']['login'].value;
    var pass = document.forms['newUser']['password'].value;
    var confPass = document.forms['newUser']['confirmPassword'].value;
    if(name == null || name == '' ){
        alert("First name must be filled out");
        return false;
    }else if(name.length <= 2){
        alert("Name have to be more than 2 symbols");
        return false;

    }else if(login == null || login == ''){
        alert("Login must be filled out");
        return false;
    }else if(pass != confPass){
        alert("your password and confirm password don't match");
        return false;
    }
}
