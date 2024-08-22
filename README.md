# DetourDetective
Detect detours on public transit systems.

Here is a guide on how to use the command-line options provided:

This tool is designed to detect vehicle detours on a given route using GPS data. It analyzes the positions of vehicles against the expected route shape and detects when a vehicle deviates from its planned route. The results are then saved to a CSV file.

## Command-Line Options

To run the tool, you will need to provide several options. Below is a description of each option and how to use it.

### Options

| Option        | Short Option | Required | Description |
|---------------|--------------|----------|-------------|
| `--route`     | `-R`         | No      | The route you want to check. This option specifies the route ID to analyze. |
| `--date`      | `-D`         | Yes      | The date you want to check the route on. The date and time should be provided in the format `yyyyMMddHH:mm:ss`. |
| `--tripId`    | `-T`         | Yes      | The trip ID you want to analyze. This is the specific trip on the route to check for detours. |
| `--vehicleId` | `-V`         | Yes      | The vehicle ID associated with the trip you are analyzing. This is the identifier for the vehicle whose GPS data will be checked for detours. |
| `--Filename`  | `-F`         | Yes      | The filename where the output CSV will be stored. This is the name of the file that will contain the detected detour information. |
| `--Directory` | `-L`         | Yes      | The directory where the CSV file will be stored. This specifies the folder path where the output file will be saved. |
| `--onRouteThreshold` | `-A` | No       | The number of times the vehicle must appear on the route after a detour to confirm that the detour has ended. Default is 3. |
| `--offRouteThreshold` | `-B` | No      | The number of times the vehicle must appear off the route to confirm that it is on a detour. Default is 10. |
| `--Distance`  | `-S`         | No       | The distance (in meters) a vehicle must be off route to be considered on a detour. Default is 20 meters. |

### Example Usage

Below is an example command to run the tool:

```bash
  -R 1234 \
  -D 2024081523:00:00 \
  -T UP_A4-Weekday-SDon-036100_X2737_704 \
  -V 5678 \
  -F detours_output.csv \
  -L /home/user/detours/ \
  -A 5 \
  -B 15 \
  -S 20
```

In this example:

- `-R 1234` specifies the route ID.
- `-D 2024081523:00:00` specifies the date and time of the trip.
- `-T UP_A4-Weekday-SDon-036100_X2737_704` specifies the trip ID.
- `-V 5678` specifies the vehicle ID.
- `-F detours_output.csv` sets the output filename to `detours_output.csv`.
- `-L /home/user/detours/` specifies the directory where the CSV file will be saved.
- `-A 5` sets the on-route threshold to 5.
- `-B 15` sets the off-route threshold to 15.
- `-S 20` sets the distance threshold.

### Example 2

```bash
  -D 2024081523:00:00 \
  -T JG_A4-Weekday-SDon-084600_B16_414 \
  -V 766 \
  -F detours_output.csv \
  -L /home/user/detours/ \
```
In this example:

- `-D 2024032623:00:00` specifies the date and time of the trip.
- `-T JG_A4-Weekday-SDon-084600_B16_414` specifies the trip ID.
- `-V 766` specifies the vehicle ID.
- `-F detours_output.csv` sets the output filename to `detours_output.csv`.
- `-L /home/user/detours/` specifies the directory where the CSV file will be saved.

Output

![DetourAppTestDataOnMap1](https://github.com/user-attachments/assets/c57d5b18-1fef-434e-9a0c-18018839b6f0)

Green points represent the shape of the trip. The yellow points are the detoured points we were given in our CSV file when we run the detour detector.

### Example 3

- This example will test all the trips on the route and date provided. Each detour will be returned in it's own CSV file.

```bash
  -R B43
  -D 2024032623:00:00 \
  -L /home/user/detours/ \
```

In this example:

- '-R B43' specifies the route.
- '-D 2024032623:00:00' specifies the date and time.
- `-L /home/user/detours/` specifies the directory where the CSV file will be saved.

![image](https://github.com/user-attachments/assets/7085edd2-ddc3-4275-9736-3d5ac2651aac)

The file name is made up of the date, route id , trip id and the vehicle id.

### Notes

- Make sure the directory specified with `-L` exists before running the tool, or the output will not be saved.
- The tool will generate a CSV file in the specified directory with the results of the detour detection.

### Used to read data into database

- https://github.com/ember-technology-ltd/gtfsrdb
- https://github.com/OpenTransitTools/gtfsdb_realtime

### Acknowledgements
This project was completed as part of Google Summer of Code 2024. Special thanks to William Wong from the MTA in New York for the invaluable support throughout this project.
