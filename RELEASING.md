# Releases steps

1. Checkout `origin/main`.
2. Update the `CHANGELOG.md` file with the changes of this release (the format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/).
    * Copy the template for the next unreleased version at the top.
    * Rename the previous unreleased version to the new version.
3. Update the version in `gradle.properties` and remove the `-SNAPSHOT` suffix.
4. Commit the changes, create a tag and push it:
   ```
   git commit -am "Releasing 0.1.0."
   git tag 1.0.0
   git push && git push --tags
   ```
5. Run the `release` github action to publish the release to maven.
6. Create a new release on GitHub with the same tag and write release notes.
7. Update the version in `gradle.properties` and add the `-SNAPSHOT` suffix.
8. Commit the change and push it:
   ```
   git commit -am "Prepare next development version."
   git push
   ```
