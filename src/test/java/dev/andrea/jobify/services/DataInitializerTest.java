package dev.andrea.jobify.services;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.boot.test.context.SpringBootTest;

import dev.andrea.jobify.models.JobType;
import dev.andrea.jobify.models.Phase;
import dev.andrea.jobify.repositories.JobTypeRepository;
import dev.andrea.jobify.repositories.PhaseRepository;

@SpringBootTest
public class DataInitializerTest {

    @Mock
    private JobTypeRepository jobTypeRepository;

    @Mock
    private PhaseRepository phaseRepository;

    @InjectMocks
    private DataInitializer dataInitializer;

    @Test
    void init_ShouldInsertJobTypesAndPhases_WhenRepositoriesAreEmpty() {
        when(jobTypeRepository.count()).thenReturn(0L);
        when(phaseRepository.count()).thenReturn(0L);

        dataInitializer.init();

        verify(jobTypeRepository).save(new JobType("Remote"));
        verify(jobTypeRepository).save(new JobType("Hybrid"));
        verify(jobTypeRepository).save(new JobType("On-Site"));

        verify(phaseRepository).save(new Phase("Exploring"));
        verify(phaseRepository).save(new Phase("Submitted"));
        verify(phaseRepository).save(new Phase("Reviewing"));
        verify(phaseRepository).save(new Phase("Interview"));
        verify(phaseRepository).save(new Phase("Tech interview"));
        verify(phaseRepository).save(new Phase("Final interview"));
        verify(phaseRepository).save(new Phase("Offer"));
        verify(phaseRepository).save(new Phase("Hired"));
    }

    @Test
    void init_ShouldNotInsertJobTypesAndPhases_WhenRepositoriesAreNotEmpty() {
        when(jobTypeRepository.count()).thenReturn(3L);
        when(phaseRepository.count()).thenReturn(8L);

        dataInitializer.init();

        verify(jobTypeRepository, never()).save(new JobType("Remote"));
        verify(jobTypeRepository, never()).save(new JobType("Hybrid"));
        verify(jobTypeRepository, never()).save(new JobType("On-Site"));

        verify(phaseRepository, never()).save(new Phase("Exploring"));
        verify(phaseRepository, never()).save(new Phase("Submitted"));
        verify(phaseRepository, never()).save(new Phase("Reviewing"));
        verify(phaseRepository, never()).save(new Phase("Interview"));
        verify(phaseRepository, never()).save(new Phase("Tech interview"));
        verify(phaseRepository, never()).save(new Phase("Final interview"));
        verify(phaseRepository, never()).save(new Phase("Offer"));
        verify(phaseRepository, never()).save(new Phase("Hired"));
    }
}