import math
import random
import numpy as np
import matplotlib.pyplot as plt
import tkinter as tk

def calculate_intersection(x, y, px, py, L):
    # Calculate the intersection point with the straight segments
    if abs(x) >= L/2:
        py = -py  # Reflection off the straight segments

    # Calculate the next position based on the intersection point and momentum
    x += px
    y += py

    return x, y, px, py

def simulate_stadium_billiard(n, L):
    # Define constants
    r = 1
    epsilon = 1e-6

    # Initialize particle position and momentum
    x = random.uniform(-r, r)
    y = random.uniform(-r, r)
    px = random.uniform(-1, 1)
    py = math.sqrt(1 - px**2) if abs(px) < 1 - epsilon else random.choice([-1, 1])

    # Initialize list to store reflection points
    reflection_points = []

    # Simulate the particle's path
    for _ in range(n):
        # Check if the particle is out of the circle
        if math.sqrt(x**2 + y**2) > r:
            break

        # Calculate the next position from the intersection point
        x, y, px, py = calculate_intersection(x, y, px, py, L)

        # Store the reflection point coordinates
        reflection_points.append((x, y, px, py))

    return reflection_points

def test_uniformity(reflection_points, M):
    # Count the number of entries in each bin
    bin_counts, _ = np.histogram(reflection_points, bins=M)

    # Compute the mean and variance of the number of entries in the bins
    mean_count = np.mean(bin_counts)
    var_count = np.var(bin_counts)

    return bin_counts, mean_count, var_count

def draw_path(reflection_points):
    # Create a GUI window
    root = tk.Tk()
    root.title("Stadium Billiard Path")

    # Create a canvas for drawing the path
    canvas_width = 400
    canvas_height = 400
    canvas = tk.Canvas(root, width=canvas_width, height=canvas_height)
    canvas.pack()

    # Draw the path of the particle
    for i in range(len(reflection_points)):
        x, y, px, py = reflection_points[i]
        color = "blue" if px > 0 else "red"
        canvas.create_oval(x * canvas_width - 2, y * canvas_height - 2, x * canvas_width + 2, y * canvas_height + 2, fill=color)

    # Start the GUI event loop
    root.mainloop()

# Set the number of reflections and length of the straight lines
n = 100  # Number of reflections
L = 1.0  # Length of the straight lines
M = 10  # Number of bins

# Simulate the stadium billiard
reflection_points = simulate_stadium_billiard(n, L)

# Test the uniformity of the reflection points
bin_counts, mean_count, var_count = test_uniformity(reflection_points, M)

# Plot the histogram of bin counts
fig = plt.figure()
ax = fig.add_subplot(111)
ax.bar(range(M), bin_counts)
ax.set_xlabel("Bin")
ax.set_ylabel("Count")
ax.set_title("Histogram of Bin Counts")

# Display the plot
plt.show()

# Print the mean and variance of the number of entries in the bins
print("Mean Count:", mean_count)
print("Variance of Count:", var_count)

# Draw the path of the particle using the GUI
draw_path(reflection_points)
