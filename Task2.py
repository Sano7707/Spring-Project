import math
import random
import tkinter as tk
from turtle import Turtle, Screen


class CircularBilliardGUI(tk.Tk):
    def __init__(self):
        super().__init__()

        self.title("Circular Billiard Simulation")
        self.geometry("800x600")

        self.reflections = 10
        self.path = None
        self.reversed_path = None

        self.create_widgets()

    def create_widgets(self):
        # Canvas to display the simulation
        self.canvas = tk.Canvas(self, width=600, height=600)
        self.canvas.pack(side=tk.TOP, pady=10)

        # Start simulation button
        self.start_button = tk.Button(self, text="Start Simulation", command=self.start_simulation)
        self.start_button.place(relx=0.5, rely=0.5, anchor=tk.CENTER)

        # Number of reflections entry
        self.reflections_entry = tk.Entry(self, width=5)
        self.reflections_entry.insert(tk.END, str(self.reflections))
        self.reflections_entry.place(relx=0.5, rely=0.55, anchor=tk.CENTER)

        # Reverse path button
        self.reverse_button = tk.Button(self, text="Reverse Path", command=self.reverse_path)
        self.reverse_button.place(relx=0.5, rely=0.6, anchor=tk.CENTER)

        # Create screen object for turtle graphics
        self.screen = Screen()
        self.turtle = Turtle()
        self.turtle.speed(2.5)  # Increase the speed to draw faster

        # Draw the circle
        self.turtle.penup()
        self.turtle.goto(0, -200)
        self.turtle.pendown()
        self.turtle.circle(200)
        self.turtle.penup()

    def start_simulation(self):
        try:
            self.reflections = int(self.reflections_entry.get())
            if self.reflections < 0:
                raise ValueError
        except ValueError:
            tk.messagebox.showerror("Invalid Input", "Number of reflections must be a positive integer.")
            return

        # Clear the canvas
        self.canvas.delete("all")

        # Clear previous path
        self.path = []

        # Reset the turtle
        self.turtle.clear()

        # Generate random initial position inside the circle
        angle = random.uniform(0, 2 * math.pi)
        radius = random.uniform(0, 200)
        x = radius * math.cos(angle) / 200  # Scale the coordinates to [0, 1]
        y = radius * math.sin(angle) / 200  # Scale the coordinates to [0, 1]

        # Generate random initial momentum
        momentum_angle = random.uniform(0, 2 * math.pi)
        px = math.cos(momentum_angle)
        py = math.sin(momentum_angle)

        # Draw the initial position of the particle
        self.turtle.goto(x * 200, y * 200)  # Scale back the coordinates to match the circle's radius
        self.turtle.pendown()
        self.turtle.color("blue")
        self.turtle.dot(5)

        # Simulate the circular billiard
        self.path.append((x, y))

        gravitational_force = 9.8  # Gravitational force value (adjust as needed)

        for reflection in range(self.reflections):
            a = px ** 2 + py ** 2
            b = 2 * (x * px + y * py)
            c = x ** 2 + y ** 2 - 1  # Circle radius is 1
            discriminant = b ** 2 - 4 * a * c

            if discriminant < 0:
                discriminant = 0  # Treat negative discriminant as zero

            t = (-b + math.sqrt(discriminant)) / (2 * a)
            x += t * px
            y += t * py

            angle_incidence = math.atan2(py, px)

            # Calculate the angle of reflection
            angle_reflection = math.atan2(y, x) - 2 * angle_incidence

            # Calculate the new momentum components
            px = (y ** 2 - x ** 2) * math.cos(angle_reflection) - 2 * x * y * math.sin(angle_reflection)
            py = -2 * x * y * math.cos(angle_reflection) + (x ** 2 - y ** 2) * math.sin(angle_reflection)

            # Apply gravitational force
            py -= gravitational_force / 200

            self.path.append((x, y))
            self.turtle.goto(x * 200, y * 200)  # Scale back the coordinates to match the circle's radius
            self.turtle.pendown()
            self.turtle.color("blue")
            self.turtle.dot(5)

            # Print the reflection number and coordinates
            print(f"Reflection {reflection + 1}: ({x:.2f}, {y:.2f})")

        # Update the screen
        self.screen.update()

    def reverse_path(self):
        if self.path is None:
            tk.messagebox.showerror("No Path", "Please start the simulation first.")
            return

        # Reverse the path
        self.reversed_path = self.path[::-1]

        # Create turtle object for reversed path
        turtle_reversed = Turtle()
        turtle_reversed.speed(2.5)  # Increase the speed to draw faster

        # Draw the reversed path on the existing graph
        for i in range(len(self.reversed_path) - 1):
            start = self.reversed_path[i]
            end = self.reversed_path[i + 1]
            turtle_reversed.goto(start[0] * 200, start[1] * 200)  # Scale back the coordinates to match the circle's radius
            turtle_reversed.pendown()
            turtle_reversed.color("red")
            turtle_reversed.dot(5)
            turtle_reversed.goto(end[0] * 200, end[1] * 200)  # Scale back the coordinates to match the circle's radius
            turtle_reversed.penup()

        # Update the screen
        turtle_reversed.screen.update()


if __name__ == "__main__":
    app = CircularBilliardGUI()
    app.mainloop()
