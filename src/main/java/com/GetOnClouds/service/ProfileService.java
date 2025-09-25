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

        if(profileRepository.existsByClerkId(profileDTO.getClerkId())){
             return updateProfile(profileDTO);
        }

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

    public ProfileDTO updateProfile(ProfileDTO profileDTO){
        ProfileDocument existingProfile= profileRepository.findByClerkId(profileDTO.getClerkId());
        if(existingProfile!=null){
            if(profileDTO.getEmail()!=null && !profileDTO.getEmail().isEmpty()){
                existingProfile.setEmail(profileDTO.getEmail());
            }
            if(profileDTO.getFirstName()!=null &&profileDTO.getFirstName().isEmpty()){
                existingProfile.setFirstName((profileDTO.getFirstName()));
            }
            if(profileDTO.getLastName()!=null &&profileDTO.getLastName().isEmpty()){
                existingProfile.setLastName((profileDTO.getLastName()));
            }
            if(profileDTO.getPhotoUrl()!=null &&profileDTO.getPhotoUrl().isEmpty()){
                existingProfile.setPhotoUrl((profileDTO.getPhotoUrl()));
            }
            profileRepository.save(existingProfile);

             return ProfileDTO.builder()
                    .id(existingProfile.getId())
                    .email(existingProfile.getEmail())
                    .firstName(existingProfile.getFirstName())
                    .lastName(existingProfile.getLastName())
                    .credits(existingProfile.getCredits())
                    .photoUrl(existingProfile.getPhotoUrl())
                    .build();
        }
        return null;
    }

    public boolean existsByClerkId(String clerkId){
        return profileRepository.existsByClerkId(clerkId);
    }
}
