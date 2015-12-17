package fr.xephi.authme.command.executable.login;

import fr.xephi.authme.process.Management;
import fr.xephi.authme.settings.Settings;
import fr.xephi.authme.util.WrapperMock;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Collections;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;

/**
 * Test for {@link LoginCommand}.
 */
public class LoginCommandTest {

    private static Management managementMock;

    @Before
    public void initializeAuthMeMock() {
        WrapperMock wrapper = WrapperMock.createInstance();

        Settings.captchaLength = 10;
        managementMock = mock(Management.class);
        Mockito.when(wrapper.getAuthMe().getManagement()).thenReturn(managementMock);
    }

    @Test
    public void shouldStopIfSenderIsNotAPlayer() {
        // given
        CommandSender sender = mock(BlockCommandSender.class);
        LoginCommand command = new LoginCommand();

        // when
        command.executeCommand(sender, new ArrayList<String>());

        // then
        Mockito.verify(managementMock, never()).performLogin(any(Player.class), anyString(), anyBoolean());
    }

    @Test
    public void shouldCallManagementForPlayerCaller() {
        // given
        Player sender = mock(Player.class);
        LoginCommand command = new LoginCommand();

        // when
        command.executeCommand(sender, Collections.singletonList("password"));

        // then
        Mockito.verify(managementMock).performLogin(eq(sender), eq("password"), eq(false));
    }

}
