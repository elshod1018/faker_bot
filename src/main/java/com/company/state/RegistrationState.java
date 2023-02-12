package com.company.state;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RegistrationState implements State {
    USERNAME,
    PASSWORD
}
