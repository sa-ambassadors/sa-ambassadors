(function() {

        console.log("test");

        let usernameIsValid = false;
        let emailIsValid = false;
        let passwordIsValid = false;
        let passwordsMatch = false;
        let usernameField = document.querySelector("#username");
        let userWarning = document.querySelector("#user-warning");
        let emailField = document.querySelector("#email");
        let emailWarning = document.querySelector("#email-warning");
        let passwordField = document.querySelector("#password");
        let passwordWarning = document.querySelector("#password-warning");
        let passwordFieldChecker = document.querySelector("#password-checker");
        let passwordCheckerWarning = document.querySelector("#password-checker-warning");

        usernameField.addEventListener('keyup', checkIfUserExistsInDatabase);
        usernameField.addEventListener('keydown', checkIfUserExistsInDatabase);
        emailField.addEventListener('keyup', checkIfEmailExistsInDatabase);
        passwordField.addEventListener('keyup', declarePasswordValidIfValid);
        passwordField.addEventListener('keyup', declarePasswordsMatchIfMatch);
        passwordFieldChecker.addEventListener('keyup', declarePasswordsMatchIfMatch);

        usernameField.addEventListener('keyup', allowUserToSubmitIfAllChecksAreMet);
        usernameField.addEventListener('keydown', allowUserToSubmitIfAllChecksAreMet);
        emailField.addEventListener('keyup', allowUserToSubmitIfAllChecksAreMet);
        passwordField.addEventListener('keyup', allowUserToSubmitIfAllChecksAreMet);
        passwordFieldChecker.addEventListener('keyup', allowUserToSubmitIfAllChecksAreMet);


        function checkIfUserExistsInDatabase() {
            fetch("api/v1/user/" + usernameField.value).then(response => {
                response.json().then(data => {
                    declareUsernameAsValidIfDoesNotExistInDB(data);
                    console.log(data);
                })
            })
        }

        function checkIfEmailExistsInDatabase() {
            fetch("api/v1/email/" + emailField.value).then(response => {
                response.json().then(data => {
                    declareEmailAsValidIfDoesNotExistInDB(data);
                    console.log(data);
                })
            })
        }

        function declareUsernameAsValidIfDoesNotExistInDB(data) {
            if (usernameField.value.length < 6 || usernameField.value.length > 20) {
                userWarning.innerHTML = "<p class='red-text'>Username must be between 6 and 20 characters!</p>";
                usernameIsValid = false;
                allowUserToSubmitIfAllChecksAreMet();
            } else if (data) {
                userWarning.innerHTML = "<p class='red-text'>Username already exists!</p>"
                usernameIsValid = false;
                allowUserToSubmitIfAllChecksAreMet();
            } else if (!data) {
                userWarning.innerHTML = "<p class='green-text'>Username is looking good.</p>";
                usernameIsValid = true;
                allowUserToSubmitIfAllChecksAreMet();
            }
        }

        function declareEmailAsValidIfDoesNotExistInDB(data) {
            if (!validateEmail(emailField.value)) {
                emailWarning.innerHTML = "<p class='red-text'>Email needs to be in correct format!</p>";
                emailIsValid = false;
                allowUserToSubmitIfAllChecksAreMet();
            } else if (data) {
                console.log(data);
                emailWarning.innerHTML = "<p class='red-text'>Email already exists!</p>";
                emailIsValid = false;
                allowUserToSubmitIfAllChecksAreMet();
            } else if (validateEmail(emailField.value) && !data) {
                emailWarning.innerHTML = "<p class='green-text'>Email looks a-okay.</p>";
                emailIsValid = true;
                allowUserToSubmitIfAllChecksAreMet();
            }

        }

        function declarePasswordValidIfValid() {
            if (passwordField.value.length < 6 || passwordField.value.length > 20) {
                passwordWarning.innerHTML = "<p class='red-text'>Password needs to be between 6 and 20 characters!</p>"
                passwordIsValid = false;
                allowUserToSubmitIfAllChecksAreMet();
            } else if (passwordField.value.length >= 6 && passwordField.value.length < 20) {
                passwordWarning.innerHTML = "<p class='green-text'>Password length looks about right.</p>"
                passwordIsValid = true;
                allowUserToSubmitIfAllChecksAreMet();
            }

        }

        function declarePasswordsMatchIfMatch() {
            if (passwordField.value === passwordFieldChecker.value) {
                passwordCheckerWarning.innerHTML = "<p class='green-text'>Passwords match.</p>";
                passwordsMatch = true;
                allowUserToSubmitIfAllChecksAreMet();
            } else if (passwordField.value !== passwordFieldChecker.value) {
                passwordCheckerWarning.innerHTML = "<p class='red-text'>Passwords don't match!</p>";
                passwordsMatch = false;
                allowUserToSubmitIfAllChecksAreMet();
            }

        }

        function allowUserToSubmitIfAllChecksAreMet() {
            if (usernameIsValid && emailIsValid && passwordIsValid && passwordsMatch) {
                document.querySelector("#submit").disabled = false;
            } else document.querySelector("#submit").disabled = true;
        }

        function validateEmail(email) {
            let re = /\S+@\S+\.\S+/;
            return re.test(email);
        }


})();