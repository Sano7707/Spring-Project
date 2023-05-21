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

        # Create screen object for turtle graphics
        screen = Screen()
        screen.clear()

        # Create turtle object
        turtle = Turtle()
        turtle.speed(2.5)  # Increase the speed to draw faster

        # Draw the circle
        turtle.penup()
        turtle.goto(0, -200)
        turtle.pendown()
        turtle.circle(200)
        turtle.penup()

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
        turtle.goto(x * 200, y * 200)  # Scale back the coordinates to match the circle's radius
        turtle.pendown()
        turtle.color("blue")
        turtle.dot(5)

        # Simulate the circular billiard
        self.path = []
        self.path.append((x, y))

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

            px, py = (y ** 2 - x ** 2) * px - 2 * x * y * py, -2 * x * y * px + (x ** 2 - y ** 2) * py

            self.path.append((x, y))
            turtle.goto(x * 200, y * 200)  # Scale back the coordinates to match the circle's radius
            turtle.pendown()
            turtle.color("blue")
            turtle.dot(5)

            # Print the reflection number and coordinates
            print(f"Reflection {reflection + 1}: ({x:.2f}, {y:.2f})")

        # Update the screen
        screen.update()

    def reverse_path(self):
        if self.path is None:
            tk.messagebox.showerror("No Path", "Please start the simulation first.")
            return

        # Reverse the path
        self.reversed_path = self.path[::-1]

        # Create turtle object
        turtle = Turtle()
        turtle.speed(2.5)  # Increase the speed to draw faster

        # Draw the reversed path on the existing graph
        for i in range(len(self.reversed_path) - 1):
            start = self.reversed_path[i]
            end = self.reversed_path[i + 1]
            turtle.goto(start[0] * 200, start[1] * 200)  # Scale back the coordinates to match the circle's radius
            turtle.pendown()
            turtle.color("red")
            turtle.dot(5)
            turtle.goto(end[0] * 200, end[1] * 200)  # Scale back the coordinates to match the circle's radius
            turtle.penup()

        # Update the screen
        turtle.screen.update()


if __name__ == "__main__":
    app = CircularBilliardGUI()
    app.mainloop()
