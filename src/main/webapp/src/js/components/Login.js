/**
 * Created by odilon-jr on 25/07/17.
 */

import  React, { Component } from 'react';
import '../../css/components/login/style.css';

class Login extends Component {
    render() {
        return (
            <div id="slick-login">
                <label for="username">username</label><input type="text" name="username" className="placeholder"
                                                             placeholder="login@login.com"/>
                <label for="password">password</label><input type="password" name="password" className="placeholder"
                                                             placeholder="password"/>
                <input type="submit" value="Entrar"/>
            </div>
        );
    }
}

export default Login;
