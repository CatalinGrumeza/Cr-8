//package com.Cr_8.repositories;
//
//import java.util.Optional;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import com.Cr_8.entities.Target;
//
///**
// * Repository interface for managing  Target entities.
// */
//public interface TargetRepo extends JpaRepository<Target, Integer> {
//
//    /**
//     * Finds a Target by its ID.
//     *
//     * @param id the ID of the target to find.
//     * @return an Optional containing the target if found, or empty otherwise.
//     */
//    Optional<Target> findTargetById(int id);
//
//    /**
//     * Finds a Target by its description.
//     *
//     * @param description the description of the target to find.
//     * @return an Optional containing the target if found, or empty otherwise.
//     */
//    Optional<Target> findTargetByDescription(String description);
//}
//
