package com.GetOnClouds.service;

import com.GetOnClouds.document.ProfileDocument;
import com.GetOnClouds.dto.ProfileDTO;
import com.GetOnClouds.repository.ProfileRepository;
import com.mongodb.DuplicateKeyException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository; // ✅ final

    public ProfileDTO createProfile(ProfileDTO profileDTO) {

        ProfileDocument profile = ProfileDocument.builder()
                .clerkId(profileDTO.getClerkId())
                .email(profileDTO.getEmail())
                .firstName(profileDTO.getFirstName())
                .lastName(profileDTO.getLastName())
                .credits(5)
                .photoUrl(profileDTO.getPhotoUrl())
                .createdAt(Instant.now())
                .build();


            profileRepository.save(profile);


         return ProfileDTO.builder()
                .id(profile.getId())
                .clerkId(profile.getClerkId())
                .email(profile.getEmail())
                .firstName(profile.getFirstName())
                .lastName(profile.getLastName())
                .credits(profile.getCredits())
                .photoUrl(profile.getPhotoUrl())
                .createdAt(profile.getCreatedAt())
                .build();
    }
}
