package dev.andrea.jobify.services;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import dev.andrea.jobify.DTOs.ApplicationDTO;
import dev.andrea.jobify.models.Application;
import dev.andrea.jobify.models.JobType;
import dev.andrea.jobify.models.User;
import dev.andrea.jobify.repositories.ApplicationRepository;
import dev.andrea.jobify.repositories.JobTypeRepository;
import dev.andrea.jobify.repositories.UserRepository;

@ExtendWith(MockitoExtension.class)
public class ApplicationServiceTest {

    @Mock
    private ApplicationRepository applicationRepository;

    @Mock
    private JobTypeRepository jobTypeRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ApplicationService applicationService;

    private User user;
    private JobType jobType;
    private ApplicationDTO applicationDTO;

    @BeforeEach
    void setUp() {
        user = new User(1L, "testuser", "password", "testuser@example.com");
        jobType = new JobType(1L, "Remote");

        applicationDTO = new ApplicationDTO();
        applicationDTO.setUserId(user.getUserId());
        applicationDTO.setCompany("Company");
        applicationDTO.setPosition("Developer");
        applicationDTO.setLocation("Location");
        applicationDTO.setRequirements("Requirements");
        applicationDTO.setJobTypeId(jobType.getJobTypeId());
        applicationDTO.setSalary(50000);
        applicationDTO.setLink("Link");
        applicationDTO.setNotes("Notes");
    }

    @Test
    void testCreateApp() {
        Application savedApplication = new Application(1L, user, "Mock Company", "Developer", "Location", "Requirements", jobType, 50000, "mockLink", "mockNotes");
        when(applicationRepository.save(any(Application.class))).thenReturn(savedApplication);

        try (MockedStatic<SecurityContextHolder> securityContextMock = Mockito.mockStatic(SecurityContextHolder.class)) {
            SecurityContext securityContext = mock(SecurityContext.class);
            Authentication authentication = mock(Authentication.class);
            
            when(securityContext.getAuthentication()).thenReturn(authentication);
            when(authentication.getName()).thenReturn("mockedUser");
            
            securityContextMock.when(() -> SecurityContextHolder.getContext()).thenReturn(securityContext);

            when(userRepository.existsByUsername("mockedUser")).thenReturn(true); // Usuario existe
            when(userRepository.findByUsername("mockedUser")).thenReturn(Optional.of(user)); // Obtener usuario mockeado
            when(jobTypeRepository.findById(anyLong())).thenReturn(Optional.of(jobType)); // Simulamos un JobType

            ApplicationDTO result = applicationService.createApp(applicationDTO);

            assertNotNull(result);
            verify(userRepository).existsByUsername("mockedUser");
            verify(userRepository).findByUsername("mockedUser");
        }
    }

    @Test
    void testDeleteApp() {
        Long applicationId = 1L;

        Application existingApplication = new Application(applicationId, user, "Mock Company", "Developer", "Location",
                "Requirements", jobType, 50000, "mockLink", "mockNotes");

        when(applicationRepository.findById(applicationId)).thenReturn(Optional.of(existingApplication));

        try (MockedStatic<SecurityContextHolder> securityContextMock = Mockito.mockStatic(SecurityContextHolder.class)) {
            Authentication authentication = mock(Authentication.class);
            when(authentication.getName()).thenReturn("mockedUser");

            SecurityContext securityContext = mock(SecurityContext.class);
            when(securityContext.getAuthentication()).thenReturn(authentication);

            securityContextMock.when(SecurityContextHolder::getContext).thenReturn(securityContext);

            when(userRepository.existsByUsername("mockedUser")).thenReturn(true);
            when(userRepository.findByUsername("mockedUser")).thenReturn(Optional.of(user));

            applicationService.deleteApp(applicationId);

            verify(applicationRepository).delete(existingApplication);
        }
    }

    @Test
    void testListAppsByUser() {
        Long userId = 1L;

        List<Application> mockApplications = List.of(
            new Application(1L, user, "Company A", "Developer", "Location A", "Requirements A", jobType, 50000, "linkA", "notesA"),
            new Application(2L, user, "Company B", "Tester", "Location B", "Requirements B", jobType, 40000, "linkB", "notesB")
        );

        when(applicationRepository.findByUser_UserId(userId)).thenReturn(mockApplications);

        try (var securityContextMock = mockStatic(SecurityContextHolder.class)) {
            Authentication authentication = mock(Authentication.class);
            when(authentication.getName()).thenReturn("mockedUser");

            SecurityContext securityContext = mock(SecurityContext.class);
            when(securityContext.getAuthentication()).thenReturn(authentication);

            securityContextMock.when(SecurityContextHolder::getContext).thenReturn(securityContext);

            when(userRepository.existsByUsername("mockedUser")).thenReturn(true);
            when(userRepository.findByUsername("mockedUser")).thenReturn(Optional.of(user));

            List<ApplicationDTO> result = applicationService.listAppsByUser();

            assertNotNull(result);
            assertEquals(2, result.size());

            assertEquals("Company A", result.get(0).getCompany());
            assertEquals("Company B", result.get(1).getCompany());

            verify(userRepository).existsByUsername("mockedUser");
            verify(userRepository).findByUsername("mockedUser");
            verify(applicationRepository).findByUser_UserId(userId);
        }
    }

    @Test
    void testGetAppById() {
        Long applicationId = 1L;

        Application mockApplication = new Application(
            applicationId,
            user,
            "Mock Company",
            "Developer",
            "Mock Location",
            "Mock Requirements",
            jobType,
            60000,
            "mockLink",
            "mockNotes"
        );

        when(applicationRepository.findById(applicationId)).thenReturn(Optional.of(mockApplication));

        try (MockedStatic<SecurityContextHolder> securityContextMock = Mockito.mockStatic(SecurityContextHolder.class)) {
            Authentication authentication = mock(Authentication.class);
            when(authentication.getName()).thenReturn("mockedUser");

            SecurityContext securityContext = mock(SecurityContext.class);
            when(securityContext.getAuthentication()).thenReturn(authentication);

            securityContextMock.when(SecurityContextHolder::getContext).thenReturn(securityContext);

            when(userRepository.existsByUsername("mockedUser")).thenReturn(true);
            when(userRepository.findByUsername("mockedUser")).thenReturn(Optional.of(user));

            ApplicationDTO result = applicationService.getAppById(applicationId);

            assertNotNull(result);

            assertEquals(applicationId, result.getApplicationId());
            assertEquals("Mock Company", result.getCompany());
            assertEquals("Developer", result.getPosition());
            assertEquals("Mock Location", result.getLocation());
            assertEquals(60000, result.getSalary());
            assertEquals("mockLink", result.getLink());
            assertEquals("mockNotes", result.getNotes());

            verify(userRepository).existsByUsername("mockedUser");
            verify(userRepository).findByUsername("mockedUser");
            verify(applicationRepository).findById(applicationId);
        }
    }

    @Test
    void testGetAppByKeyword() {
        String keyword = "Developer";

        Application mockApplication1 = new Application(
            1L,
            user,
            "Mock Company 1",
            "Developer",
            "Mock Location 1",
            "Mock Requirements 1",
            jobType,
            60000,
            "mockLink1",
            "mockNotes1"
        );

        Application mockApplication2 = new Application(
            2L,
            user,
            "Mock Company 2",
            "Senior Developer",
            "Mock Location 2",
            "Mock Requirements 2",
            jobType,
            70000,
            "mockLink2",
            "mockNotes2"
        );

        List<Application> mockApplications = List.of(mockApplication1, mockApplication2);

        when(applicationRepository.findByKeywordAndUserId(keyword, user.getUserId())).thenReturn(mockApplications);

        try (MockedStatic<SecurityContextHolder> securityContextMock = Mockito.mockStatic(SecurityContextHolder.class)) {
            Authentication authentication = mock(Authentication.class);
            when(authentication.getName()).thenReturn("mockedUser");

            SecurityContext securityContext = mock(SecurityContext.class);
            when(securityContext.getAuthentication()).thenReturn(authentication);

            securityContextMock.when(SecurityContextHolder::getContext).thenReturn(securityContext);

            when(userRepository.existsByUsername("mockedUser")).thenReturn(true);
            when(userRepository.findByUsername("mockedUser")).thenReturn(Optional.of(user));

            List<ApplicationDTO> result = applicationService.getAppByKeyword(keyword);

            assertNotNull(result);
            assertEquals(2, result.size());

            assertEquals("Mock Company 1", result.get(0).getCompany());
            assertEquals("Mock Company 2", result.get(1).getCompany());
            assertEquals("Developer", result.get(0).getPosition());
            assertEquals("Senior Developer", result.get(1).getPosition());

            verify(userRepository).existsByUsername("mockedUser");
            verify(userRepository).findByUsername("mockedUser");
            verify(applicationRepository).findByKeywordAndUserId(keyword, user.getUserId());
        }
    }

    @Test
    void testCountApplicationsByUserId() {
        Long userId = 1L;
        int mockCount = 5;

        when(applicationRepository.countApplicationsByUserId(userId)).thenReturn(mockCount);

        int result = applicationService.countApplicationsByUserId(userId);

        assertEquals(mockCount, result);

        verify(applicationRepository).countApplicationsByUserId(userId);
    }

    @Test
    void testGetTotalApplicationsCount() {
        String mockedUsername = "mockedUser";
        Long userId = 1L;
        int mockTotalCount = 10;

        try (MockedStatic<SecurityContextHolder> securityContextMock = Mockito.mockStatic(SecurityContextHolder.class)) {
            SecurityContext securityContext = mock(SecurityContext.class);
            Authentication authentication = mock(Authentication.class);

            when(securityContext.getAuthentication()).thenReturn(authentication);
            when(authentication.getName()).thenReturn(mockedUsername);
            securityContextMock.when(SecurityContextHolder::getContext).thenReturn(securityContext);

            when(userRepository.existsByUsername(mockedUsername)).thenReturn(true);
            when(userRepository.findByUsername(mockedUsername)).thenReturn(Optional.of(new User(userId, mockedUsername, "password", "mocked@example.com")));
            when(applicationRepository.countByUser_UserId(userId)).thenReturn((int) mockTotalCount);

            int result = applicationService.getTotalApplicationsCount();

            assertEquals(mockTotalCount, result);

            verify(userRepository).existsByUsername(mockedUsername);
            verify(userRepository).findByUsername(mockedUsername);
            verify(applicationRepository).countByUser_UserId(userId);
        }
    }
}