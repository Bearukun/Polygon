var password = document.getElementById("password")
        , confirm_password = document.getElementById("passwordConfirm");

function validatePassword() {
    if (password.value != confirm_password.value) {
        confirm_password.setCustomValidity("Password stemmer ikke overens!");
    } else {
        confirm_password.setCustomValidity('');
    }
}
//onchange????
password.onchange = validatePassword;
//onkeyup????
confirm_password.onkeyup = validatePassword;

