
package com.quiz.quotamanage.service;

import com.quiz.quotamanage.BizException;
import com.quiz.quotamanage.data.QuotaAccountPo;
import com.quiz.quotamanage.data.UserAccountPo;
import com.quiz.quotamanage.manager.QuotaAccountManager;
import com.quiz.quotamanage.mapper.QuotaAccountMapper;
import com.quiz.quotamanage.mapper.UserAccountMapper;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class QuotaAccountServiceImplTest {

    @InjectMocks
    private QuotaAccountServiceImpl quotaAccountService;

    @Mock
    private QuotaAccountMapper quotaAccountMapper;

    @Mock
    private UserAccountMapper userAccountMapper;

    @Mock
    private QuotaAccountManager quotaAccountManager;

    @Test
    public void testInitAccount_validAccountType_accountInitializedSuccessfully() throws BizException {
        // Arrange
        Long userId = 1L;
        Byte accountType = 2;
        when(userAccountMapper.selectByUser(userId)).thenReturn(null);

        // Act
        quotaAccountService.initAccount(userId, accountType);

        // Assert
        verify(quotaAccountManager).initAccount(userId, accountType, 10000.0);
    }

    @Test
    public void testInitAccount_invalidAccountType_throwsBizException() {
        // Arrange
        Long userId = 1L;
        Byte accountType = 3;

        // Act and Assert
        Assertions.assertThrows(BizException.class, () -> quotaAccountService.initAccount(userId, accountType));
    }

    @Test
    public void testInitAccount_existingAccount_throwsBizException() {
        // Arrange
        Long userId = 1L;
        Byte accountType = 2;
        final UserAccountPo userAccountPo = UserAccountPo.builder().userId(1L).build();
        when(userAccountMapper.selectByUser(userId)).thenReturn(userAccountPo);

        // Act and Assert
        Assertions.assertThrows(BizException.class, () -> quotaAccountService.initAccount(userId, accountType));
    }


    @Test
    public void addQuotaAccount_shouldThrowBizExceptionWhenAccountDoesNotExist() throws BizException {
        // Arrange
        Long userId = 1L;
        Byte accountType = 2;

        when(userAccountMapper.selectByUser(userId)).thenReturn(null);

        // Act and Assert
        Assertions.assertThrows(BizException.class, () -> quotaAccountService.addQuotaAccount(userId, accountType));

    }

    @Test
    public void addQuotaAccount_shouldThrowBizExceptionWhenQuotaAccountExists() throws BizException {
        // Arrange
        Long userId = 1L;
        Byte accountType = 2;

        final UserAccountPo userAccountPo = UserAccountPo.builder().userId(1L).build();
        when(userAccountMapper.selectByUser(userId)).thenReturn(userAccountPo);
        final QuotaAccountPo quotaAccountPo = QuotaAccountPo.builder().userId(1L).quota(1000.0).build();
        when(quotaAccountMapper.selectByUserAndType(userId, accountType)).thenReturn(quotaAccountPo);

        // Act and Assert
        Assertions.assertThrows(BizException.class, () -> quotaAccountService.addQuotaAccount(userId, accountType));

    }

    @Test
    public void addQuotaAccount_shouldAddQuotaAccountWhenPreChecksPass() throws BizException {
        // Arrange
        Long userId = 1L;
        Byte accountType = 2;
        Double quota = 10000.0;

        final UserAccountPo userAccountPo = UserAccountPo.builder().userId(1L).build();
        when(userAccountMapper.selectByUser(userId)).thenReturn(userAccountPo);
        when(quotaAccountMapper.selectByUserAndType(userId, accountType)).thenReturn(null);

        // Act
        quotaAccountService.addQuotaAccount(userId, accountType);

        // Assert that the addQuotaAccount method of quotaAccountManager is called with the expected arguments
        verify(quotaAccountManager).addQuotaAccount(userId, accountType, quota);
    }

    @Test(expected = BizException.class)
    public void testIncreaseQuota_zeroQuota() throws BizException {
        // Mock the necessary dependencies and set up the test scenario
        Long userId = 12345L;
        Byte accountType = 1;
        Double quota = 0.0;

        // Call the method under test
        quotaAccountService.increaseQuota(userId, accountType, quota);

        // Act and Assert
        Assertions.assertThrows(BizException.class, () -> quotaAccountService.increaseQuota(userId, accountType,
                quota));
    }

    @Test
    public void testIncreaseQuotaWithNonexistentQuotaTypeThrowsException() {
        // Arrange
        Long userId = 1L;
        Byte accountType = 2;
        Double quota = 100.0;

        when(quotaAccountMapper.selectByUserAndType(userId, accountType)).thenReturn(null);

        // Act and Assert
        Assertions.assertThrows(BizException.class, () -> {
            quotaAccountService.increaseQuota(userId, accountType, quota);
        });
    }

    @Test
    public void testIncreaseQuota() throws BizException {
        // Arrange
        Long userId = 1L;
        Byte accountType = 2;
        Double quota = 100.0;

        QuotaAccountPo existedQuota = QuotaAccountPo.builder().id(123L).build();

        when(quotaAccountMapper.selectByUserAndType(userId, accountType)).thenReturn(existedQuota);

        // Act
        quotaAccountService.increaseQuota(userId, accountType, quota);

        // Assert
        verify(quotaAccountManager).updateQuota(userId, accountType, quota, existedQuota.getId());
    }

    @Test(expected = BizException.class)
    public void decreaseQuota_ThrowsExceptionWithPositiveQuota() throws BizException {
        // Arrange
        Long userId = 1L;
        Byte accountType = 2;
        Double quota = 10.0;

        // Act
        quotaAccountService.decreaseQuota(userId, accountType, quota);

        // Assert: Validate that the expected exception is thrown
        Assertions.assertThrows(BizException.class, () -> quotaAccountService.decreaseQuota(userId, accountType,
                quota));
    }

    @Test(expected = BizException.class)
    public void decreaseQuota_ThrowsExceptionIfQuotaAccountNotExist() throws BizException {
        // Arrange
        Long userId = 1L;
        Byte accountType = 2;
        Double quota = -5.0;

        when(quotaAccountMapper.selectByUserAndType(userId, accountType)).thenReturn(null);

        // Act
        quotaAccountService.decreaseQuota(userId, accountType, quota);

        // Assert: Validate that the expected exception is thrown
        Assertions.assertThrows(BizException.class, () -> quotaAccountService.decreaseQuota(userId, accountType,
                quota));
    }

    @Test(expected = BizException.class)
    public void testDecreaseQuota_InsufficientQuota() throws BizException {
        // Arrange
        Long userId = 1L;
        Byte accountType = 2;
        Double quota = -100.0;

        // Mock the `quotaAccountMapper.selectByUserAndType` method to return a quota account
        QuotaAccountPo quotaAccountPo = QuotaAccountPo.builder().userId(1L).quota(10.0).build();
        when(quotaAccountMapper.selectByUserAndType(userId, accountType)).thenReturn(quotaAccountPo);

        // Act
        quotaAccountService.decreaseQuota(userId, accountType, quota);

        // Assert: Exception should be thrown with the message "额度不足"
        Assertions.assertThrows(BizException.class, () -> quotaAccountService.decreaseQuota(userId, accountType,
                quota));
    }

    @Test
    public void testDecreaseQuota_Success() throws BizException {
        // Arrange
        Long userId = 1L;
        Byte accountType = 2;
        Double quota = -100.0;

        // Mock the `quotaAccountMapper.selectByUserAndType` method to return a quota account
        QuotaAccountPo quotaAccountPo = QuotaAccountPo.builder().userId(1L).quota(200.0).id(123L).build();
        when(quotaAccountMapper.selectByUserAndType(userId, accountType)).thenReturn(quotaAccountPo);

        // Act
        quotaAccountService.decreaseQuota(userId, accountType, quota);

        // Assert: Check that the quota is correctly updated
        verify(quotaAccountManager).updateQuota(userId, accountType, quota, quotaAccountPo.getId());
    }
}
