package com.example;

import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.launcher.listeners.TestExecutionSummary;

import static org.junit.platform.engine.discovery.DiscoverySelectors.selectClass;

public class TestRunner {
    public static void main(String[] args) {
        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
                .selectors(
                    selectClass(com.example.LibraryTest.class),
                    selectClass(com.example.BookTest.class),
                    selectClass(com.example.PatronTest.class),
                    selectClass(com.example.CatFactServiceTest.class)
                )
                .build();
        
        
        SummaryGeneratingListener listener = new SummaryGeneratingListener();
        
        Launcher launcher = LauncherFactory.create();
        launcher.registerTestExecutionListeners(listener);
        launcher.execute(request);
        
        TestExecutionSummary summary = listener.getSummary();
        
        System.out.println("Total tests run: " + summary.getTestsFoundCount());
        System.out.println("Tests passed: " + (summary.getTestsFoundCount() - summary.getTotalFailureCount()));
        System.out.println("Tests failed: " + summary.getTotalFailureCount());
        
        if (summary.getTotalFailureCount() > 0) {
            System.out.println("\nFailures:");
            summary.getFailures().forEach(failure -> 
                System.out.println(failure.getTestIdentifier().getDisplayName() + ": " + failure.getException())
            );
        }
    }
}