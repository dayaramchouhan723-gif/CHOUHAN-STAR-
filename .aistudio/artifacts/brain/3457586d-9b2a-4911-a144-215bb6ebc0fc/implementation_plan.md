# Chouhan Music v1.0 — Offline Audio Exporter, Spatial Reverb DSP & Final Release Plan

Chouhan Music v1.0 is an elite AI Music Studio created by **Dayaram Chouhan** with all 8 AI Music Engine Clones unlocked 100% Free Lifetime. This plan details the addition of the Offline Audio Exporter & Local Storage Manager, 3D Reverb Spatial Audio DSP with Advanced EQ Presets, and the Final Release APK export configuration.

---

## User Review & Critical Decisions

> [!IMPORTANT]
> The following user preferences were confirmed during Phase 1 clarification:

- **Confirmed Decision 1 (Feature Expansion)**: Add Offline Audio Exporter and Local Storage Manager to export generated tracks directly as WAV/MP3 files into user-accessible external storage directories (`Music/ChouhanMusic/`).
- **Confirmed Decision 2 (Audio DSP)**: Integrate Reverb Spatial Audio (Hall, Room, Stage, Cathedral spatial impulse responses) and Advanced 5-Band EQ Presets directly into `ChouhanMediaPlayer` and `StudioToolsScreen`.
- **Confirmed Decision 3 (Build & Release Target)**: Configure Gradle and CI/CD pipelines to output the production single APK artifact `ChouhanMusic-v1.0-by-Dayaram-Chouhan.apk` with `isPremium = true` and zero paywalls.

---

## 1. Overview & Core Concept

- **What It Does**: Provides complete offline audio file export and storage management for all generated AI tracks, combined with high-fidelity 3D Spatial Reverb DSP effects and expanded studio equalizer presets.
- **Target Audience / Persona**: Music creators, producers, and regional music enthusiasts seeking free lifetime AI song creation with studio-grade audio processing and local file ownership.
- **Key Value**: 100% free lifetime access to 8 AI music engines (Suno, Udio, Mureka, Boomy, Soundraw, AIVA, ElevenLabs, Google Lyria) with full commercial ownership by Dayaram Chouhan and local file export capability.

---

## 2. User Experience & Visual Design

### Key User Flows
1. **Offline Export Workflow**: From `HomeScreen`, `LibraryScreen`, or `FullScreenPlayer`, tapping the "Download / Export" action saves the track as a high-quality `.wav` or `.mp3` file to public storage (`/storage/emulated/0/Music/ChouhanMusic/`) with progress feedback and notification.
2. **Local Storage Manager**: A dedicated Storage tab in `LibraryScreen` listing downloaded tracks, showing file sizes, storage path, file sharing options, and deletion.
3. **Spatial Reverb & DSP Tuning**: In `StudioToolsScreen`, users toggle 3D Reverb spatial presets (Concert Hall, Acoustic Room, Studio Stage, Grand Cathedral) alongside the 4-Stem Mixer and 5-Band Equalizer.

### Visual Identity & Theme
- **Theme Palette**: Premium Royal Blue (`#0A1931`) background with Royal Gold (`#FFD700`) and Light Gold (`#FFE57F`) accents, complemented by Electric Cyan (`#00E5FF`) indicators.
- **Typography**: Bold display titles with crisp, high-contrast labels across 5 supported languages (Hindi, Urdu, Sindhi, Dhatki, English).
- **Layout Consistency**: Responsive cards with 1.5.dp gold borders, interactive ripples, and smooth Compose transitions.

---

## 3. Key Product Decisions & Trade-Offs

- **Decision 1: Android Environmental Reverb Integration**
  - *Chosen Approach*: Utilize Android's native `android.media.audiofx.PresetReverb` / `EnvironmentalReverb` attached to `ChouhanMediaPlayer`'s `AudioSessionId`.
  - *Why*: Provides hardware-accelerated, zero-latency spatial audio without adding heavy external native C++ DSP binaries.

- **Decision 2: Local Storage Access via MediaStore API**
  - *Chosen Approach*: Use Android `MediaStore.Audio.Media` and scoped storage for Android 10+ (API 29+) with fallback file output for older SDKs.
  - *Why*: Guarantees zero-permission requirements on modern Android versions while ensuring exported files appear immediately in default system music players.

---

## 4. Technical Architecture & Data Strategy

```
┌────────────────────────────────────────────────────────────────────────┐
│                        Chouhan Music v1.0 App                          │
├────────────────────────────────────────────────────────────────────────┤
│  [HomeScreen]    [CreateScreen]    [LibraryScreen]   [StudioToolsScreen] │
│         │               │                 │                  │         │
│         └───────────────┼─────────────────┴──────────────────┘         │
│                         ▼                                              │
│               ChouhanMusicViewModel                                    │
│             (isPremium = true, StateFlow)                              │
│                         │                                              │
│      ┌──────────────────┴──────────────────┐                           │
│      ▼                                     ▼                           │
│ ChouhanMediaPlayer                 LocalStorageManager                 │
│ (Equalizer, BassBoost,             (MediaStore API, WAV Exporter,      │
│  PresetReverb Spatial DSP)          Storage Directory Sync)            │
│      │                                     │                           │
│      ▼                                     ▼                           │
│ Android AudioTrack                 Public Storage                      │
│ (Session FX Engine)                (/Music/ChouhanMusic/)             │
└────────────────────────────────────────────────────────────────────────┘
```

### Key Components & State
1. **`LocalStorageManager.kt`**: Handles file copying, WAV header generation, MediaStore indexing, and storage space calculation.
2. **`ChouhanMediaPlayer.kt`**: Extended with `PresetReverb` attached to `audioSessionId` supporting presets (`PRESET_LARGEHALL`, `PRESET_ROOM`, `PRESET_MEDIUMHALL`, `PRESET_PLATE`).
3. **`StudioToolsScreen.kt`**: UI controls for Reverb Spatial Audio, 5-Band EQ Sliders, 4-Stem Separation Mixer, and Speed/Pitch sliders.
4. **`LibraryScreen.kt`**: Updated with local file storage tab and export status indicators.
